package com.ruoyi.common.utils.secret;

/**
 * Hex 加密算法
 */
public class Hex {

	public static String bytesToHex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
	
	public static String bytesToHexSpace(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
            	tmp += "0";
            }
            des += tmp+" ";
        }
        return des;
    }
	
	public static byte hexToByte(String inHex){  
		return (byte)Integer.parseInt(inHex,16);  
	} 
	
	public static byte[] hexToBytes(String inHex){  
		if(inHex==null) return null;
	    int hexlen = inHex.length();  
	    byte[] result=null;  
	    if ((hexlen & 1) == 1){  
	        //奇数  
	        hexlen++;  
	        result = new byte[(hexlen/2)];  
	        inHex="0"+inHex;  
	    }else {  
	        //偶数  
	        result = new byte[(hexlen/2)];  
	    }  
	    int j=0;  
	    for (int i = 0; i < hexlen; i+=2){  
	        result[j]=hexToByte(inHex.substring(i,i+2));  
	        j++;  
	    }  
	    return result;   
	} 
}
