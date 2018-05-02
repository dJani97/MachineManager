package hu.djani.Manager.component;

import org.springframework.stereotype.Component;

@Component
public class OsInfoComponent {

	public static final String USER_ROOT = "ROOT";
	public static final String USER_NORMAL = "NORMAL";

	public static final String OS_WINDOWS = "WINDOWS";
	public static final String OS_UNIX = "UNIX";

	public String getOsName() {
		return System.getProperty("os.name").toUpperCase().contains(OS_WINDOWS) ? OS_WINDOWS : OS_UNIX;
	}

	public boolean isRunningAsAdmin() {
		if (OS_UNIX == this.getOsName()) {
			String user_name = System.getProperty("user.name");
			if ("root".equals(user_name)) {
				return true;
			}
		} else if (OS_WINDOWS == this.getOsName()) {
			String user_name = System.getProperty("user.name");
			if ("admin".equals(user_name)) {
				return true;
			}
		} else {
			return false;
		}

		return false;
	}

}
