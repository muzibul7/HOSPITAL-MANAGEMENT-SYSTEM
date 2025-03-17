import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class SimpleHTTPServer {
    private static final int PORT = 8000;
    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put(".html", "text/html");
        MIME_TYPES.put(".css", "text/css");
        MIME_TYPES.put(".js", "text/javascript");
        MIME_TYPES.put(".jpg", "image/jpeg");
        MIME_TYPES.put(".jpeg", "image/jpeg");
        MIME_TYPES.put(".png", "image/png");
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new StaticFileHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);
        System.out.println("Open http://localhost:" + PORT + " in your browser");
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();
            
            // Default to index.html for root path
            if (requestPath.equals("/")) {
                requestPath = "/index.html";
            }

            // Remove leading slash and get the file
            String filePath = requestPath.substring(1);
            File file = new File(filePath);

            if (file.exists() && file.isFile()) {
                // Get content type based on file extension
                String contentType = getContentType(file.getName());
                exchange.getResponseHeaders().set("Content-Type", contentType);

                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.sendResponseHeaders(200, bytes.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                String response = "404 (Not Found)\n";
                exchange.sendResponseHeaders(404, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }

        private String getContentType(String fileName) {
            int lastDot = fileName.lastIndexOf('.');
            if (lastDot >= 0) {
                String extension = fileName.substring(lastDot).toLowerCase();
                String mimeType = MIME_TYPES.get(extension);
                if (mimeType != null) {
                    return mimeType;
                }
            }
            return "application/octet-stream";
        }
    }
} 