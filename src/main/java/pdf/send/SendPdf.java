package pdf.send;
import java.io.IOException;
public class SendPdf {
    public SendPdf(String filePath) throws IOException {
        String[] command = {"python", "C:\\Users\\hardik\\eclipse-workspace\\PayoutFusion\\src\\main\\java\\assets\\send_whatsapp.py", filePath};
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.start();
    }
}