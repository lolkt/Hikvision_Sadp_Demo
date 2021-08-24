package com.hikvision.sadp;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.IntByReference;

public interface HCSadpSDKByJNA extends Library {

	public static class BYTE_ARRAY extends Structure {
		public byte[] byValue;

		public BYTE_ARRAY(int iLen) {
			byValue = new byte[iLen];
		}

		@Override
		protected List<String> getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("byValue");
		}
	}
	
	public static class SADP_DEV_NET_PARAM extends Structure {
		public byte[] szIPv4Address = new byte[16];
		public byte[] szIPv4SubNetMask = new byte[16];
		public byte[] szIPv4Gateway = new byte[16];
		public byte[] szIPv6Address = new byte[128];
		public byte[] szIPv6Gateway = new byte[128];
		public short wPort;
		public byte byIPv6MaskLen; 
		public byte byDhcpEnable; 
		public short wHttpPort;
		public int	dwSDKOverTLSPort;
		public byte[] byRes = new byte[122];

		@Override
		public String toString() {
			return "SADP_DEV_NET_PARAM.szIPv4Address: " + new String(szIPv4Address) + "\n"
					+ "SADP_DEV_NET_PARAM.szIPv4SubNetMask: " + new String(szIPv4SubNetMask) + "\n"
					+ "SADP_DEV_NET_PARAM.szIPv4Gateway: " + new String(szIPv4Gateway) + "\n"
					+ "SADP_DEV_NET_PARAM.szIPv6Address: " + new String(szIPv6Address) + "\n"
					+ "SADP_DEV_NET_PARAM.szIPv6Gateway: " + new String(szIPv6Gateway) + "\n"				
					+ "SADP_DEV_NET_PARAM.byRes: " + new String(byRes) + "\n";
		}
		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("szIPv4Address", "szIPv4SubNetMask", "szIPv4Gateway", "szIPv6Address", "szIPv6Gateway", 
					"wPort", "byIPv6MaskLen", "byDhcpEnable", "wHttpPort", "dwSDKOverTLSPort", "byRes");
		}
	}
	
	public static class SADP_DEV_RET_NET_PARAM extends Structure {
		public byte byRetryModifyTime;
		public byte bySurplusLockTime; 
		public byte[] byRes = new byte[126];
		
		@Override
		public String toString() {
			return 	"SADP_DEV_RET_NET_PARAM.byRes: " + new String(byRes) + "\n";
		}
		
		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("byRetryModifyTime", "bySurplusLockTime", "byRes");
		}
	}
	
	int SADP_ModifyDeviceNetParam_V40(Pointer sMAC, Pointer sPassword, Pointer lpNetParam, Pointer lpRetNetParam, int dwOutBuffSize);
	int SADP_ModifyDeviceNetParam(Pointer sMAC, Pointer sPassword, Pointer lpNetParam);
}