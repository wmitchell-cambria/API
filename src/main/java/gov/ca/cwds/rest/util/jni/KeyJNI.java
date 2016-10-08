package gov.ca.cwds.rest.util.jni;

import gov.ca.cwds.rest.api.persistence.cms.StaffPerson;

//
// http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html
//

// COMPILE JAVA:
// javac gov/ca/cwds/rest/util/jni/KeyJNI.java

// GENERATE C HEADERS:
// javah -jni gov.ca.cwds.rest.util.jni.KeyJNI

// JAVA EXECUTE: OS X:
// java -Djava.library.path=.:/usr/lib/ gov.ca.cwds.rest.util.jni.KeyJNI

/**
 * Demonstrates calling native CWDS key generation library via JNI.
 * 
 * <h2>Steps to build and run</h2>
 * 
 * @author CWDS API Team
 */
public class KeyJNI {
	static {
		System.out.println("user.dir=" + System.getProperty("user.dir"));
		System.out.println("java.library.path=" + System.getProperty("java.library.path"));

		// keyJNI.dll (Windows), libKeyJNI.dylib (Mac), libKeyJNI.so (Unix)
		// Load native library at runtime.
		System.loadLibrary("KeyJNI");
	}

	/**
	 * Track memory to hunt memory leaks and overall memory consumption.
	 * 
	 * @return free memory in MB
	 */
	public static long calcMemory() {
		Runtime runtime = Runtime.getRuntime();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		return (freeMemory + (maxMemory - allocatedMemory)) / 1024L;
	}

	/**
	 * Utility struct class stores details of CWDS key decomposition.
	 */
	public static final class KeyDetail {
		public String key;
		public String staffId;
		public String UITimestamp;
		public String PTimestamp;
	}

	/**
	 * Generates a unique key for use within CWDS CMS based on the given staff person id.
	 * 
	 * @param staffId	the {@link StaffPerson}
	 * @return	The generated key
	 */
	public native String generateKey(String staffId);

	/**
	 * Decomposes a generated key.
	 * 
	 * @param key	the key
	 * @param kd	the key detail
	 */
	public native void decomposeKey(String key, KeyDetail kd);

	// Test Driver
	public static void main(String[] args) {
		KeyJNI inst = new KeyJNI();

		// ===================
		// GENERATE KEY:
		// ===================

		{ // Generate a key from a staff id.
			System.out.println("Java: Call JNI generateKey ... ");
			final String key = inst.generateKey("0X5");
			System.out.println("Java: key=" + key);
		}

		// ===================
		// DECOMPOSE KEY:
		// ===================

		final long startingMemory = calcMemory();

		for (int i = 0; i < 10; i++) {
			System.out.println("current memory = " + calcMemory());
			System.out.println("Java: Call JNI decomposeKey ... " + i);

			KeyDetail kd = new KeyDetail();
			inst.decomposeKey("S8eScDM0X5", kd);
			System.out.println("Java: key=" + kd.key + ", staffId=" + kd.staffId + ", UITimestamp=" + kd.UITimestamp
					+ ", PTimestamp=" + kd.PTimestamp);
		}

		System.out.println("used memory = " + (calcMemory() - startingMemory));
	}
}
