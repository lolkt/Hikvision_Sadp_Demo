package com.hikvision.sadp;

import com.sun.jna.Native;

public enum HCSadpSDKJNAInstance 
{	
	CLASS;
	private static HCSadpSDKByJNA sadpSdk = null;
	/**
	 * get the instance of HCNetSDK
	 * @return the instance of HCNetSDK
	 */
	public static HCSadpSDKByJNA getInstance()
	{
		if (null == sadpSdk)
		{
			synchronized (HCSadpSDKByJNA.class)
			{
				sadpSdk = (HCSadpSDKByJNA) Native.loadLibrary("sadp",
						HCSadpSDKByJNA.class);
			}			
		}
		return sadpSdk;
	}
}