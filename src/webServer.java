import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class webServer {
	
	public static void main(String[] args) {
		ServerSocket server = null;
		boolean shutdown = false;

		try {
			server = new ServerSocket(1236);
			System.out.println("Port bound. Accepting connections");

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		while (!shutdown) {
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;

			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();
				String answer = "";
				int num = 0;

				int n = input.read();
				byte[] data = new byte[n];				
				input.read(data);

				String clientInput = new String(data, StandardCharsets.UTF_8);
				clientInput.replace("\n", "");

				try {
					num = Integer.parseInt(clientInput);
					System.out.println("Client said: " + num);
				} catch (NumberFormatException e) {
					if (clientInput.equalsIgnoreCase("shutdown")) {
						System.out.println("Shutting down...");
						String response = "Shutting down...";
						output.write(response.length());
						output.write(response.getBytes());
						shutdown = true;
						

					} else {
						String response = "Please enter an integer";
						output.write(response.length());
						output.write(response.getBytes());

					}
				}

				if (num < 0) {

					String response = "Please enter a non-negative number";
					output.write(response.length());
					output.write(response.getBytes());

				} else {

					answer = prime(num);

					String response = "Server answer is:  " + answer;
					output.write(response.length());
					output.write(response.getBytes());
				}

				client.close();

				
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

		}

	}

	public static String prime (int n) {
		String response = "";
		
		 boolean flag = false;
		    for (int i = 2; i <= n / 2; ++i) {
		      // condition for nonprime number
		      if (n % i == 0) {
		        flag = true;
		        break;
		      }
		    }
		    
		    if (!flag)
		        return n + " is a prime number.";
		      else
		        return n + " is not a prime number.";

	}

}
