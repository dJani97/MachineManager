package hu.djani.Manager.component;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PingComponent {

	private static int DEFAULT_TIMEOUT = 150;

	public boolean isReachable(String targetAddress, int timeout) {
		boolean reachable = false;

		try {
			reachable = InetAddress.getByName(targetAddress).isReachable(timeout);
		} catch (UnknownHostException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reachable;

	}

	public boolean isReachable(String targetAddress) {
		return this.isReachable(targetAddress, DEFAULT_TIMEOUT);

	}

}
