package pdf.send;
import java.io.IOException;
class SendPdf {
    public SendPdf(String filePath) throws IOException {
        String[] command = {"python", "D:\\bills\\send_whatsapp.py", filePath};
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.start();
    }
}