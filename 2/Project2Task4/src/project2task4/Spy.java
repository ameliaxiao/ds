
package project2task4;

import java.security.MessageDigest;

/**
 *
 * @author Amelia
 */
public class Spy {
    //private fields containing the information about a spy
    private String ID;
    private String name;
    private String salt = "cmu";//salt used to store the password with
    private String passHash;
    private String location = "-79.945389,40.444216,0.00000";//original location
    /**
     * constructor to create a new spy instance
     * @param name string representing spy's name
     * @param id string representing spy's id
     * @param password  string representing spy's password
     */
    public Spy(String name, String id, String password){
        this.name = name;
        ID = id;
        //encrypt the password using MD% hashcode
        try{
            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] input = (salt+password).getBytes();
            String hex = getHexString(input);
            passHash = hex;
            
        } catch(Exception e){
            
        }
    }
    /**
     * method to change the location of a spy
     * @param loc string representing the current location of the spy
     */
    public void changeLocation(String loc){
        location = loc;
    }
    /**
     * method to get hex string(code taken from former project)
     * @param b the byte array containing the hex
     * @return the string representing the hex
     * @throws Exception 
     */
    public String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
    /**
     * function to get the current location of a spy
     * @return string representing the location
     */
    public String getLoc(){
        return location;
    }
    /**
     * method to check if the password is correct
     * @param pass string representing the password typed in
     * @return boolean value indicates if the password is correct or not
     */
    public boolean checkPassword(String pass){
        String hex = "";
        try{
            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] input = (salt+pass).getBytes();
            hex = getHexString(input);
            
        } catch(Exception e){
            
        }
        if(hex.equals(passHash)){
            return true;
        } else 
            return false;
    }
    /**
     * function to get the name of the spy
     * @return string representing the name
     */
    public String getName(){
        return name;
    }
    
}
