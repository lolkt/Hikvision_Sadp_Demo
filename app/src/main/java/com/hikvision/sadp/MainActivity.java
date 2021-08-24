package com.hikvision.sadp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.jna.Pointer;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private Toast mToast = null;

    private String maskIpAddress;


    private Button startbn;

    private Button cancelBn;

    private Button UpdateBn;
    private Button ActivateBn;

    private Button HCPlatformBn;

    // private OneStepWifiConfigurationManager oneStepWifiConfigurationManager;
    private Sadp m_sadp;
    private DeviceFindCallBack g_fPlaybackCallBack = null;
    private DeviceFindCallBack_V40 g_fPlaybackCallBack_V40 = null;
    private EditText ssidEdit = null;

    private TextView contentTextView = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //contentTextView.append("\n\n" + msg.obj);
                    showToast(msg.obj.toString());
                    break;

                case 2:
                    // cancelBn.performClick();
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (m_sadp == null) {
            m_sadp = new Sadp();
        }
        //开始搜索
        startbn = (Button) findViewById(R.id.startbn);
        startbn.setOnClickListener(new MyClickListener());
        //停止搜索
        cancelBn = (Button) findViewById(R.id.cancelbn);
        cancelBn.setOnClickListener(new StopClickListener());


        //修改IP
        UpdateBn = (Button) findViewById(R.id.updatebn);
        UpdateBn.setOnClickListener(new UpdateIPClickListener());


        //激活设备
        ActivateBn = (Button) findViewById(R.id.activatebn);
        ActivateBn.setOnClickListener(new ActivateClickListener());

        //修改HCPatform状态
        HCPlatformBn = (Button) findViewById(R.id.HCPlatformbn);
        HCPlatformBn.setOnClickListener(new HCPlatformClickListener());


    }

    //回调函数处理
    private DeviceFindCallBack_V40 getFindDeviceCallback_V40() {
        DeviceFindCallBack_V40 oDeviceFindCbf_V40 = new DeviceFindCallBack_V40() {
            @Override
            public void fDeviceFindCallBack_V40(SADP_DEVICE_INFO_V40 lpDeviceInfo_V40) {
                SADP_DEVICE_INFO_V40 struDevInfo_V40 = lpDeviceInfo_V40;
                String strIpv4 = new String(lpDeviceInfo_V40.struSadpDeviceInfo.szIPv4Address);
                String strSerialNO = new String(lpDeviceInfo_V40.struSadpDeviceInfo.szSerialNO);
                String strMac = new String(lpDeviceInfo_V40.struSadpDeviceInfo.szMAC);
                int dwDeviceType = lpDeviceInfo_V40.struSadpDeviceInfo.dwDeviceType;
                String szDevDesc = new String(lpDeviceInfo_V40.struSadpDeviceInfo.szDevDesc);

                Log.i(TAG, "Callback ipv4: " + strIpv4 + ",szMac :" + strMac + ",iResult:" + lpDeviceInfo_V40.struSadpDeviceInfo.iResult + "dwSDKOverTLSPort:" + lpDeviceInfo_V40.dwSDKOverTLSPort);
                Log.i(TAG, "Callback strSerialNO: " + strSerialNO + ",dwDeviceType:" + dwDeviceType + ",szDevDesc :" + szDevDesc);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "Callback ipv4: " + strIpv4 + " szMac :" + strMac + " iResult:" + lpDeviceInfo_V40.struSadpDeviceInfo.iResult + "dwSDKOverTLSPort:" + lpDeviceInfo_V40.dwSDKOverTLSPort + "\n";
                mHandler.sendMessage(msg);
            }
        };
        return oDeviceFindCbf_V40;
    }

    //回调函数处理
    private DeviceFindCallBack getFindDeviceCallback() {
        DeviceFindCallBack oDeviceFindCbf = new DeviceFindCallBack() {
            @Override
            public void fDeviceFindCallBack(SADP_DEVICE_INFO lpDeviceInfo) {
                SADP_DEVICE_INFO struDevInfo = lpDeviceInfo;
                String strIpv4 = new String(lpDeviceInfo.szIPv4Address);
                String strSerialNO = new String(lpDeviceInfo.szSerialNO);
                String strMac = new String(lpDeviceInfo.szMAC);

                Log.i(TAG, "Callback ipv4: " + strIpv4 + " szMac :" + strMac + " iResult:" + lpDeviceInfo.iResult);

                Log.i(TAG, "Callback strSerialNO: " + strSerialNO + " dwDeviceType:" + lpDeviceInfo.dwDeviceType);
                //
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "Callback ipv4: " + strIpv4 + " szMac :" + strMac + " iResult:" + lpDeviceInfo.iResult + "\n";
                mHandler.sendMessage(msg);
            }
        };
        return oDeviceFindCbf;
    }

    //开始搜索
    public class MyClickListener implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            m_sadp = new Sadp();
            m_sadp.SADP_SetLogToFile(3, "/mnt/sdcard/sadp", false);
//	 		m_sadp.SADP_SetLogToFile(3,"/mnt/shell/emulated/0/sadp",false);
//	 		g_fPlaybackCallBack = getFindDeviceCallback();
//	 		boolean bRet = m_sadp.SADP_Start_V30(g_fPlaybackCallBack);
            g_fPlaybackCallBack_V40 = getFindDeviceCallback_V40();
            boolean bRet = m_sadp.SADP_Start_V40(g_fPlaybackCallBack_V40);
            m_sadp.SADP_SetAutoRequestInterval(30);
            int iTemp = m_sadp.SADP_SendInquiry();
        }

    }

    //停止搜索
    public class StopClickListener implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            m_sadp.SADP_Stop();
        }

    }

    //修改IP
    public class UpdateIPClickListener implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            try {

//				SADP_DEV_NET_PARAM  strNetParam = new SADP_DEV_NET_PARAM();
//				String str = "192.168.1.65";				
//				System.arraycopy(str.getBytes(),0, strNetParam.szIPv4Address, 0, str.length());
//				str = "255.255.255.0";				
//				System.arraycopy(str.getBytes(),0, strNetParam.szIPv4SubnetMask, 0, str.length());
//				str = "192.168.1.1";				
//				System.arraycopy(str.getBytes(),0, strNetParam.szIPv4Gateway, 0, str.length());
//
//				strNetParam.byIPv6MaskLen = 64;
//				strNetParam.wHttpPort = 80;
//				strNetParam.wPort = 8000;
//				int iRet = m_sadp.SADP_ModifyDeviceNetParam("64-db-8b-27-25-48", "hik12345", strNetParam);
//				if(iRet == 0)
//				{
//					Log.e(TAG,"SADP_ModifyDeviceNetParam fail Errorcode is:"+ m_sadp.SADP_GetLastError());
//					Message msg = new Message();
//					msg.what = 1;
//					msg.obj = "修改失败  errorcode" + m_sadp.SADP_GetLastError() + "\n";
//					mHandler.sendMessage(msg);
//				}
//				else
//				{
//					Log.e(TAG,"SADP_ModifyDeviceNetParam Success!");
//					Message msg = new Message();
//					msg.what = 1;
//					msg.obj = "修改成功" + "\n";
//					mHandler.sendMessage(msg);
//				}

                //JNA模式
                HCSadpSDKByJNA.BYTE_ARRAY sMAC = new HCSadpSDKByJNA.BYTE_ARRAY("64-db-8b-27-25-48".length() + 1);
                System.arraycopy("64-db-8b-27-25-48".getBytes(), 0, sMAC.byValue, 0, "64-db-8b-27-25-48".length());
                sMAC.write();
                HCSadpSDKByJNA.BYTE_ARRAY sPassword = new HCSadpSDKByJNA.BYTE_ARRAY("hik12345".length() + 1);
                System.arraycopy("hik12345".getBytes(), 0, sPassword.byValue, 0, "hik12345".length());
                sPassword.write();

                HCSadpSDKByJNA.SADP_DEV_NET_PARAM struNetParam = new HCSadpSDKByJNA.SADP_DEV_NET_PARAM();
                System.arraycopy("192.168.1.65".getBytes(), 0, struNetParam.szIPv4Address, 0, "192.168.1.65".length());
                System.arraycopy("255.255.255.0".getBytes(), 0, struNetParam.szIPv4SubNetMask, 0, "255.255.255.0".length());
                System.arraycopy("192.168.1.1".getBytes(), 0, struNetParam.szIPv4Gateway, 0, "192.168.1.1".length());
                System.arraycopy("::".getBytes(), 0, struNetParam.szIPv6Address, 0, "::".length());
                System.arraycopy("::".getBytes(), 0, struNetParam.szIPv6Gateway, 0, "::".length());
                struNetParam.byDhcpEnable = 1;
                struNetParam.byIPv6MaskLen = 64;
                struNetParam.wHttpPort = 80;
                struNetParam.wPort = 8000;
                struNetParam.write();
                Pointer lpNetParam = struNetParam.getPointer();

                HCSadpSDKByJNA.SADP_DEV_RET_NET_PARAM struRetNetParam = new HCSadpSDKByJNA.SADP_DEV_RET_NET_PARAM();
                Pointer lpRetNetParam = struRetNetParam.getPointer();

                int bRet = HCSadpSDKJNAInstance.getInstance().SADP_ModifyDeviceNetParam_V40(sMAC.getPointer(), sPassword.getPointer(), lpNetParam, lpRetNetParam, 128);
                //int bRet = HCSadpSDKJNAInstance.getInstance().SADP_ModifyDeviceNetParam(sMAC.getPointer(), sPassword.getPointer(), lpNetParam);
                if (bRet == 0) {
                    struRetNetParam.read();

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = "修改失败  errorcode" + m_sadp.SADP_GetLastError() + "\n";
                    mHandler.sendMessage(msg);
                } else if (bRet == 1) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = "修改成功" + "\n";
                    mHandler.sendMessage(msg);
                }
            } catch (Exception err) {
                Log.e(TAG, "error: " + err.toString());
            }
        }

    }

    //激活设备
    public class ActivateClickListener implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Message msg = new Message();
            msg.what = 1;
            try {
                int iRet = m_sadp.SADP_ActivateDevice("iDS-2CD6810F/C20171215AAWR156541444", "hik12345");//63
                if (iRet == 0) {
                    //Log.e(TAG, "SADP_ActivateDevice fail Errorcode is:" + m_sadp.SADP_GetLastError());
                    Log.e(TAG, "SADP_ActivateDevice fail Errorcode is:" + m_sadp.SADP_GetLastError());
                    msg.obj = "SADP_ActivateDevice fail Errorcode is:" + m_sadp.SADP_GetLastError();

                } else {
                    Log.e(TAG, "SADP_ActivateDevice Success!");
                    msg.obj = "SADP_ActivateDevice Success!";
                }
            } catch (Exception err) {
                Log.e(TAG, "error: " + err.toString());
                msg.obj = err.toString();
            }
            mHandler.sendMessage(msg);
        }
    }

    //修改HCPlatform状态
    public class HCPlatformClickListener implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub

            try {

                SADP_VERIFICATION_CODE_INFO struVerificationCode = new SADP_VERIFICATION_CODE_INFO();
                struVerificationCode.dwSize = 160;
                String strPassword = "hik12345";
                String strVerificationCode = "hik12345";
                System.arraycopy(strVerificationCode.getBytes(), 0, struVerificationCode.szVerificationCode, 0, strVerificationCode.length());

                System.arraycopy(strPassword.getBytes(), 0, struVerificationCode.szPassword, 0, strPassword.length());
                SADP_DEV_LOCK_INFO struCodeLockInfo = new SADP_DEV_LOCK_INFO();
                boolean iCodeRet = false;
//				boolean iCodeRet = m_sadp.SADP_SetDeviceConfig("DS-2CD4032FWD-A20140826CCCH477810783",m_sadp.SADP_SET_VERIFICATION_CODE, struVerificationCode, struCodeLockInfo);//25
                ///boolean iCodeRet = m_sadp.SADP_SetDeviceConfig("DS-2CD2820F20160716AAWR624618246",m_sadp.SADP_SET_VERIFICATION_CODE, struVerificationCode, struCodeLockInfo);//25

                if (iCodeRet) {
                    //Log.e(TAG, "SADP_ActivateDevice fail Errorcode is:" + m_sadp.SADP_GetLastError());
                    Log.i(TAG, "SADP_SET_VERIFICATION_CODE Success!");

                } else {
                    int iError = m_sadp.SADP_GetLastError();
                    if (iError == Sadp.SADP_PASSWORD_ERROR) {
                        Log.e(TAG, "SADP_PASSWORD_ERROR, you can try :" + struCodeLockInfo.byRetryTime);
                    } else if (iError == Sadp.SADP_LOCKED) {
                        Log.e(TAG, "SADP_LOCKED, Minute  :" + struCodeLockInfo.bySurplusLockTime);
                    } else {
                        Log.e(TAG, "SADP_SET_VERIFICATION_CODE fail Errorcode is:" + m_sadp.SADP_GetLastError());
                    }
                }


                SADP_HCPLATFORM_STATUS_INFO struHCPlatform = new SADP_HCPLATFORM_STATUS_INFO();
                struHCPlatform.dwSize = 152;
                struHCPlatform.byEnableHCPlatform = 1;

                System.arraycopy(strPassword.getBytes(), 0, struHCPlatform.szPassword, 0, strPassword.length());

                SADP_DEV_LOCK_INFO struLockInfo = new SADP_DEV_LOCK_INFO();
                boolean iRet = false;
//				iRet = m_sadp.SADP_SetDeviceConfig("DS-2CD4032FWD-A20140826CCCH477810783",m_sadp.SADP_SET_HCPLATFORM_STATUS, struHCPlatform, struLockInfo);//25
                //int iRet = m_sadp.SADP_ActivateDevice("DS-2CD2Q10FD-IW20140314AAWR455578130", "hik12345");//194
//				int iRet = SADPJNAInstance.getInstance().SADP_ActivateDevice("DS-2CD2Q10FD-IW20140314AAWR455578130", "hik12345");//194
                if (iRet) {
                    //Log.e(TAG, "SADP_ActivateDevice fail Errorcode is:" + m_sadp.SADP_GetLastError());

                    Log.i(TAG, "SADP_SET_HCPLATFORM_STATUS Success!");

                } else {
                    int iError = m_sadp.SADP_GetLastError();
                    if (iError == Sadp.SADP_PASSWORD_ERROR) {
                        Log.e(TAG, "SADP_PASSWORD_ERROR, you can try :" + struLockInfo.byRetryTime);
                    } else if (iError == Sadp.SADP_LOCKED) {
                        Log.e(TAG, "SADP_LOCKED, Minute  :" + struLockInfo.bySurplusLockTime);
                    } else {
                        Log.e(TAG, "SADP_SET_HCPLATFORM_STATUS fail Errorcode is:" + m_sadp.SADP_GetLastError());
                    }
                }

                //paramCfg(m_iLogID);
            } catch (Exception err) {
                Log.e(TAG, "error: " + err.toString());
            }
        }

    }


    protected void showToast(String text) {
        if (isFinishing()) {
            return;
        }
        if (text != null && !"".equals(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

}
