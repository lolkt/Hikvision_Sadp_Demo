package com.hikvision.sadp;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public interface HCSadpSDKByJNA extends Library {
	
	public static final int SADP_GET_EZVIZ_UNBIND_STATUS = 24;
	public static final int SADP_EZVIZ_UNBIND = 25;
	
	
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
	
	public static class SADP_RESET_PARAM extends Structure {
		public byte[] szCode = new byte[256];
		public byte[] szAuthFile = new byte[260];
		public byte[] szPassword = new byte[16];
		public byte byEnableSyncIPCPW; 
		public byte[] byRes = new byte[511];

		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("szCode", "szAuthFile", "szPassword", "byEnableSyncIPCPW", "byRes");
		}
	}
	
	public static class SADP_RESET_PARAM_V40 extends Structure {
		public int	dwSize;
		public byte byResetType;
		public byte byEnableSyncIPCPW; 
		public byte[] byRes2 = new byte[2];
		public byte[] szPassword = new byte[16];
		public byte[] szCode = new byte[256];
		public byte[] szAuthFile = new byte[260];
		public byte[] szGUID = new byte[128];
		public SADP_SECURITY_QUESTION_CFG struSecurityQuestionCfg = new SADP_SECURITY_QUESTION_CFG();
		public byte[] byRes = new byte[512];

		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("dwSize", "byResetType", "byEnableSyncIPCPW", "byRes2", "szPassword", 
					"szCode", "szAuthFile", "szGUID", "struSecurityQuestionCfg", "byRes");
		}
	}
	
	public static class SADP_SECURITY_QUESTION_CFG extends Structure {
		public int	dwSize;
		public SADP_SINGLE_SECURITY_QUESTION_CFG[] struSecurityQuestionCfg = new SADP_SINGLE_SECURITY_QUESTION_CFG[32];
		public byte[] szPassword = new byte[16];
		public byte[] byRes = new byte[512];

		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("dwSize", "struSecurityQuestionCfg", "byRes");
		}
	}
	
	public static class SADP_SINGLE_SECURITY_QUESTION_CFG extends Structure {
		public int	dwSize;
		public int	dwId;
		public byte[] szAnswer = new byte[256];
		public byte byMark;
		public byte[] byRes = new byte[127];

		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("dwSize", "dwId", "szAnswer", "byMark", "byRes");
		}
	}
	
	public static class SADP_ENCRYPT_STRING extends Structure {
		public int	dwEncryptStringSize;
		public byte[] szEncryptString = new byte[256];
		public byte[] byRes = new byte[128];

		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("dwEncryptStringSize", "szEncryptString", "byRes");
		}
	}
	
	public static class SADP_EZVIZ_UNBIND_STATUS extends Structure {
		public byte byResult;
		public byte[] byRes = new byte[127];

		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("byResult", "byRes");
		}
	}
	
	public static class SADP_EZVIZ_UNBIND_PARAM extends Structure {
		public byte[] szPassword = new byte[16];
		public byte[] byRes = new byte[256];

		@Override
		protected List getFieldOrder() {
			// TODO Auto-generated method stub
			return Arrays.asList("szPassword", "byRes");
		}
	}
	

	
	int SADP_SetDeviceConfig(Pointer sDevSerialNO, int dwCommand, Pointer lpInBuffer, int  dwInBuffSize, Pointer lpOutBuffer, int dwOutBuffSize);
	int SADP_GetDeviceConfig(Pointer sDevSerialNO, int dwCommand, Pointer lpInBuffer, int  dwinBuffSize, Pointer lpOutBuffer, int dwOutBuffSize);
	int SADP_ModifyDeviceNetParam_V40(Pointer sMAC, Pointer sPassword, Pointer lpNetParam, Pointer lpRetNetParam, int dwOutBuffSize);
	int SADP_ModifyDeviceNetParam(Pointer sMAC, Pointer sPassword, Pointer lpNetParam);
	int SADP_ResetPasswd(Pointer sDevSerialNO, Pointer pResetParam);
	int SADP_ResetPasswd_V40(Pointer sDevSerialNO, Pointer pResetParam, Pointer pRetResetParam);
}