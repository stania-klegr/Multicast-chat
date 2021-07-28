import java.net.*;
import java.io.*;


class Client {

	public static void main(String args[])
	{
		try
		{
			//connect to the multicast group
			InetAddress group = InetAddress.getByName("239.0.202.1");
			MulticastSocket s = new MulticastSocket(40202);
			s.joinGroup(group);
		
			//start the reciver
			ReciverWorker reciver = new ReciverWorker(s);
			reciver.start();
		
			//loop forever and send anything typed into the console
			while(true)
			{
				//get the line from the console
				String msg = System.console().readLine();
				//pack and send the packet
				DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, 40202);
				s.send(hi);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Boom! something broke: " + ex);
		}
	}
} 

class ReciverWorker extends Thread{

	MulticastSocket s; 
	//constructor
	public ReciverWorker(MulticastSocket s)
	{
		this.s = s;
	}
	
    public void run()
    {
    	System.out.println("running the session");   
		try
		{
			//loops forever and prints any recived packet to the console
			while(true)
			{
				byte[] buf = new byte[1000];
				DatagramPacket recv = new DatagramPacket(buf, buf.length);
				s.receive(recv);
				//print the message and the ip that it was from
				System.out.println(recv.getAddress() + " " + new String(recv.getData()));
			}
		}
		catch(Exception ex)
		{
			System.out.println("Boom! something broke: " + ex);
		}   	
    }     
}













































