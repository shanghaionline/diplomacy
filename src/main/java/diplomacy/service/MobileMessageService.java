package diplomacy.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileMessageService {
    private String account;
    private String password;

    public int sendMessage(String mobile, String message) throws IOException {
        URL url = new URL("http://222.66.24.235/ctmp/HttpSendSM");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        String data = params("acsrccode", account, "pswd", password, "needstatus", "false",
                "mobile", mobile, "msg", message);
        conn.getOutputStream().write(data.getBytes("utf-8"));
        return parseResult(conn.getInputStream());
    }

    private int parseResult(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[128];
        int ret = -1;
        inputStream.read(buffer);
        String result = new String(buffer, "utf-8");
        Pattern pattern = Pattern.compile("^\\d+,(\\d+)");
        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            ret = Integer.valueOf(matcher.group(1));
        }
        return ret;
    }

    private String params(String... ps) throws UnsupportedEncodingException {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < ps.length; i += 2) {
            String data = URLEncoder.encode(ps[i + 1], "utf-8");
            if (i != 0) buf.append("&");
            buf.append(ps[i]).append("=").append(data);
        }
        return buf.toString();
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
