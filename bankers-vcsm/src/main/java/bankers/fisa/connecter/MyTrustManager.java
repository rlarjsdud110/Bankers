package bankers.fisa.connecter;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MyTrustManager implements TrustManager,X509TrustManager{
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
	public boolean isServerTrusted(X509Certificate[] certs) {
		return true;
	}
	public boolean isClientTrusted(X509Certificate[] certs) {
		return true;
	}
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		return;
	}
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		return;
	}
}
