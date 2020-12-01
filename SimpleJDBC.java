import java.util.*;
import java.util.logging.SimpleFormatter;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SimpleJDBC {

	private static boolean dbDriverLoaded = false;
	private static Connection conn = null;
	// handling the keyboard inputs through a BufferedReader
	// This variable became global for your convenience.
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final String SQL_INSERT = "INSERT INTO [dbo].CarRent (CARRENTID, RENTALNAME,ISLANDID) VALUES (?,?,?)";
	private static final String SQL_INSERTHOT = "INSERT INTO [dbo].Hotel (ID, HOTELNAME,ISLANDID,RATING) VALUES (?,?,?,?)";
	private static final String SQL_INSERTAIR = "INSERT INTO [dbo].Airlines (AIRCODE,NAME,URL) VALUES (?,?,?)";
	private static final String SQL_INSERTFLG = "INSERT INTO [dbo].Flights (ID,[FROM], [TO], AIRLINECODE, FROMTIME, TOTIME, PRICE, [VOLCANO PRICE]) VALUES (?,?,?,?,?,?,?,?)";
	private static final String SQL_INSERTAPT = "INSERT INTO [dbo].Airport (AIRPORTCODE,AIRPORTNAME) VALUES (?,?)";
	private static final String SQL_INSERTClient = "INSERT INTO [dbo].Client (SYSTEMID,LNAME,FNAME,ZIPCODE,CITY,NUMBER,STREET,PHONE,EMAIL) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_INSERTWorker = "INSERT INTO [dbo].Workers (SSN,LNAME,FNAME,POSSITION,PHONE,USERNAME,PASSWORD,SPHONE,SUPERSSN,STREET,ZIPCODE) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_INSERTReservation = "INSERT INTO [dbo].Reservation ([RESERVATION NUMBER],[WORKER ID],[CLIENT ID],VISANUM,[CHECK IN],[CHECK OUT],[TIME],PLACE,CORRECTION,[ROOM ID],[CAR ID],[FLIGHT ID]) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_INSERTPayment = "INSERT INTO [dbo].Payment ( ID,SALARY,[TIME],CHEQUE) VALUES (?,?,?,?)";
	private static final String SQL_INSERTRoom = "INSERT INTO [dbo].HotelRoom ( ROOMID,HOTELID,PRICE,[VOLCANO PRICE],TYPE) VALUES (?,?,?,?,?)";
	private static final String SQL_INSERTCar = "INSERT INTO [dbo].Cars ( CARID,COMPANYID,PRICE,[VOLCANO PRICE],TYPE) VALUES (?,?,?,?,?)";
	private static final String SQl_DROP = "DROP TABLE Airlines; DROP TABLE CarRent; DROP TABLE Flights; DROP TABLE Hotel; DROP TABLE HotelRoom; DROP TABLE Island; DROP TABLE Cars; DROP TABLE Payment; DROP TABLE Reservation; DROP TABLE Workers; DROP TABLE Client; DROP TABLE Airport;";

	/**
	 * A method that returns a connection to MS SQL server DB
	 *
	 * @return The connection object to be used.
	 */
	private Connection getDBConnection() {
		//String dbConnString = "jdbc:sqlserver://mssql.cs.ucy.ac.cy:1433;databaseName=pmelio01;user=pmelio01;password=699sfPQV;";
	String dbConnString = "jdbc:sqlserver://mssql.cs.ucy.ac.cy:1433;databaseName=cyiann03;user=cyiann03;password=Ss49cG9a;";
	//String dbConnString = "jdbc:sqlserver://mssql.cs.ucy.ac.cy:1433;databaseName=cyiann03;user=cyiann03;password=Ss49cG9a;";

		if (!dbDriverLoaded)
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				dbDriverLoaded = true;
			} catch (ClassNotFoundException e) {
				System.out.println("Cannot load DB driver!");
				return null;
			}

		try {
			if (conn == null)
				conn = DriverManager.getConnection(dbConnString);
			else if (conn.isClosed())
				conn = DriverManager.getConnection(dbConnString);
		} catch (SQLException e) {
			System.out.print("Cannot connect to the DB!\nGot error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void insertCarRent() {
		int i = 1;
		int j = 0;
		// String name = "";
		File file = new File("car-rental-names.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERT);
					preparedStatement.setInt(1, i);
					preparedStatement.setString(2, code[j]);
					j++;
					preparedStatement.setString(3, code[j]);

					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;
					i++;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error CarRent .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for Car-Rent not found");
		}
	}

	public void insertHotel() {
		int i = 1;
		int j = 0;
		// String name = "";
		File file = new File("hotel-names.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTHOT);
					preparedStatement.setInt(1, i);
					preparedStatement.setString(2, code[j]);
					j++;
					int iid = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(3, iid);
					j++;
					int rating = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(4, rating);

					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row); // 1
					j = 0;
					i++;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Hotel .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for Hotel not found");
		}
	}

	private void insertAirlines() {
		int j = 0;
		File file = new File("airlines.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTAIR);
					preparedStatement.setString(1, code[j]);
					j++;
					preparedStatement.setString(2, code[j]);
					j++;
					preparedStatement.setString(3, code[j]);

					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;

				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error CarRent .");
				}
			}

		} catch (FileNotFoundException e1) {
			System.err.println("File for Flights not found");
		}

	}

	public void insertAirport() {
		int j = 0;
		File file = new File("Airport.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(":");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTAPT);
					preparedStatement.setString(1, code[j]);
					j++;
					preparedStatement.setString(2, code[j]);

					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;

				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Airport .");
				}
			}

		} catch (FileNotFoundException e1) {
			System.err.println("File for Airport not found");
		}

	}

	public void insertFlights() {
		int i = 1;
		int j = 0;
		File file = new File("flights.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTFLG);
					preparedStatement.setInt(1, i);
					preparedStatement.setString(2, code[j]);
					j++;
					preparedStatement.setString(3, code[j]);
					j++;
					preparedStatement.setString(4, code[j]);
					j++;
					java.sql.Time t = null;
					t = Time.valueOf(code[j]);
					preparedStatement.setTime(5, t);
					j++;
					t = Time.valueOf(code[j]);
					preparedStatement.setTime(6, t);
					j++;
					preparedStatement.setString(7, code[j]);
					j++;
					preparedStatement.setString(8, code[j]);
					

					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;
					i++;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Flights .");
				}
			}

		} catch (FileNotFoundException e1) {
			System.err.println("File for Flights not found");
		}

	}

	public void insertClient() {
		int i = 1;
		int j = 0;
		// String name = "";
		File file = new File("Clients.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTClient);
					preparedStatement.setInt(1, i);
					preparedStatement.setString(2, code[j]);
					j++;
					preparedStatement.setString(3, code[j]);
					j++;
					int Zip = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(4, Zip);
					j++;
					preparedStatement.setString(5, code[j]);
					j++;
					int Number = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(6, Number);
					j++;
					preparedStatement.setString(7, code[j]);
					j++;
					int Phone = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(8, Phone);
					j++;
					preparedStatement.setString(9, code[j]);
					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;
					i++;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Clients .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for Clients not found");
		}
	}

	public void insertWorkers() {

		int j = 0;
		// String name = "";
		File file = new File("Workers.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTWorker);
					int SSN = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(1, SSN);
					j++;
					preparedStatement.setString(2, code[j]);
					j++;
					preparedStatement.setString(3, code[j]);
					j++;
					preparedStatement.setString(4, code[j]);
					j++;
					int Number = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(5, Number);

					j++;
					preparedStatement.setString(6, code[j]);
					j++;
					preparedStatement.setString(7, code[j]);
					j++;
					int Phone = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(8, Phone);
					j++;
					int SuperSSN = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(9, SuperSSN);
					j++;
					preparedStatement.setString(10, code[j]);
					j++;
					int ZipCode = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(11, ZipCode);

					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;

				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Workers .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for workers not found");
		}
	}

	public void insertReservation() {
		int i = 1;
		int j = 0;
		// String name = "";
		File file = new File("Reservations.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				java.sql.Date d = null;
				java.sql.Time t = null;

				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTReservation);
					int rID = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(1, rID);
					j++;
					int WorkerID = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(2, WorkerID);
					j++;
					int ClientID = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(3, ClientID);
					j++;
					preparedStatement.setString(4, code[j]);
					j++;
					// date

					d = Date.valueOf(code[j]);

					preparedStatement.setDate(5, d);
					j++;
					d = Date.valueOf(code[j]);

					preparedStatement.setDate(6, d);
					j++;
					t = Time.valueOf(code[j]);
					preparedStatement.setTime(7, t);
					j++;
					preparedStatement.setString(8, code[j]);
					j++;
					int Phone = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(9, Phone);
					j++;
					int Roomid = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(10, Roomid);
					j++;
					int Carid = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(11, Carid);
					j++;
					int Flightid = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(12, Flightid);

					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;
					i++;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Clients .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for Res not found");
		}
	}

	public void insertPayment() {

		int j = 0;
		// String name = "";
		File file = new File("Payment.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTPayment);
					int WorkerID = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(1, WorkerID);
					j++;
					int Salary = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(2, Salary);
					j++;
					java.sql.Time t = null;

					t = Time.valueOf(code[j]);
					preparedStatement.setTime(3, t);
					j++;
					int cheque = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(4, cheque);
					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;

				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Payment .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for Payment not found");
		}
	}

	public void insertRoom() {
		int i = 1;
		int j = 0;
		// String name = "";
		File file = new File("Room.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTRoom);
					preparedStatement.setInt(1, i);
					int HotelID = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(2, HotelID);
					j++;
					
					preparedStatement.setString(3, code[j]);
					j++;
					preparedStatement.setString(4, code[j]);
					j++;
					preparedStatement.setString(5, code[j]);
					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;
					i++;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error room .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for room not found");
		}
	}

	public void insertCars() {
		int i = 1;
		int j = 0;
		// String name = "";
		File file = new File("car.txt");
		Scanner read;
		try {
			read = new Scanner(file);

			while (read.hasNextLine()) {
				String line = read.nextLine();
				// name = read.nextLine();
				String[] code = line.split(",");
				// Statement stmt;
				// System.out.print(code[j]);
				// j++;
				// System.out.println(code[j]);
				PreparedStatement preparedStatement;
				try {
					preparedStatement = conn.prepareStatement(SQL_INSERTCar);
					preparedStatement.setInt(1, i);
					int CompanyID = Integer.parseInt(code[j].trim());
					preparedStatement.setInt(2, CompanyID);
					
					j++;
					preparedStatement.setString(3, code[j]);
					j++;
					preparedStatement.setString(4, code[j]);
					j++;
					preparedStatement.setString(5, code[j]);
					int row = preparedStatement.executeUpdate();

					// rows affected
					System.out.println(row);
					j = 0;
					i++;
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Prepared Statement error Car .");
				}
			}
		} catch (FileNotFoundException e1) {
			System.err.println("File for Car not found");
		}
	}

	public void dropTables() {
		PreparedStatement stm;
		try {
			stm = conn.prepareStatement(SQl_DROP);
			int row = stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void HotelDetails() {
		String HotelName;
		String IslandName;
		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.HotelDescr(?,?) }");

			System.out.println("Dose Onoma Xenodoxiou : ");
			HotelName = sc.nextLine();
			cs.setString(1, HotelName);
			System.out.println("Dose Onoma Nisiou : ");
			IslandName = sc.nextLine();
			cs.setString(2, IslandName);
			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				int HotelID = t.getInt(1);
				System.out.print("Hotel ID : " + HotelID + " ");
				IslandName = t.getString(2);
				System.out.println("IslandName : " + IslandName + " ");
				String Desc = t.getString(3);
				System.out.println("Description :" + Desc + " ");
				int IslandID = t.getInt(4);
				System.out.print("Island ID :" + IslandID + " ");
				HotelName = t.getString(5);
				System.out.print("HotelName :" + HotelName + " ");
				int Rating = t.getInt(7);
				System.out.println("Rating : " + Rating);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void SearchFlight() {

		java.sql.Date d = null;
		java.sql.Time ti = null;

		String From;
		String To;
		int VolcanoPriceMin;
		int VolcanoPriceMax;

		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.SearchFlight(?,?,?,?,?,?) }");

			System.out.println("Hmerominia : ");
			String date = sc.nextLine();
			d = Date.valueOf(date);
			cs.setDate(1, d);
			System.out.println("Ora : ");
			String time = sc.nextLine();
			ti = Time.valueOf(time);
			cs.setTime(2, ti);
			System.out.println("From : ");
			From = sc.nextLine();
			cs.setString(3, From);
			System.out.println("To : ");
			To = sc.nextLine();
			cs.setString(4, To);
			System.out.println("Dose VolcanoPrice min: ");
			VolcanoPriceMin = sc.nextInt();
			cs.setInt(5, VolcanoPriceMin);
			System.out.println("Dose VolcanoPrice max: ");
			VolcanoPriceMax = sc.nextInt();
			cs.setInt(6, VolcanoPriceMax);
			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				int VolcanoPrice = t.getInt(1);
				System.out.print("VolcanoPrice : " + VolcanoPrice + " ");
				d = t.getDate(2);
				System.out.println("Date : " + d + " ");

				System.out.print("Time :" + ti + " ");
				To = t.getString(4);
				System.out.print("To :" + To + " ");
				From = t.getString(5);
				System.out.print("From :" + From + " ");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void WorkerSearch() {

		String ClientFName;
		String ClientLName;
		int SystemID;
		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call [dbo].SearchWorker2(?,?,?,?) }");
            
			System.out.println("Dose SSN : ");
			SystemID = sc.nextInt();
			cs.setInt(1, SystemID);

			System.out.println("Dose Epitheto Ergazomenou : ");
			ClientFName = sc.next();
			cs.setString(2, ClientFName);
			System.out.println("Dose Onoma Ergazomenou : ");
			ClientLName = sc.next();
			cs.setString(3, ClientLName);
			System.out.println("Dose ZipCode : ");
			int Zip = sc.nextInt();
			cs.setInt(4, Zip);
			
			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				SystemID = t.getInt(1);
				System.out.print("Worker ID : " + SystemID + " ");
				ClientLName = t.getString(2);
				System.out.println("ClientFName : " + ClientLName + " ");
				String ClientFname = t.getString(3);
				System.out.print("ClientLName :" + ClientFname + " ");
				String possition = t.getString(4);
				System.out.print("Possition :"+possition+" " );
				int Phone = t.getInt(5);
				System.out.print("Phone :" + Phone + " ");
				String Username = t.getString(6);
				System.out.print("ClientLName :" + Username + " ");
				String Password = t.getString(7);
				System.out.print("ClientLName :" +Password + " ");
				int SPhone = t.getInt(8);
				System.out.print("Phone :" + SPhone + " ");
				int SssnID = t.getInt(9);
				System.out.print("SSSN : " + SssnID + " ");
				String Street = t.getString(10);
				System.out.print("Street : " + Street + " ");
				int ZipCode = t.getInt(11);
				System.out.print("Zip Code :" + ZipCode + " ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public static void ClientlDescr() {

		String ClientFName;
		String ClientLName;
		int SystemID;
		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.ClientlDescr(?,?,?) }");

			System.out.println("Dose Onoma Pelati : ");
			ClientFName = sc.nextLine();
			cs.setString(1, ClientFName);
			System.out.println("Dose Epitheto Pelati : ");
			ClientLName = sc.nextLine();
			cs.setString(2, ClientLName);
			System.out.println("Dose SystemID : ");
			SystemID = sc.nextInt();
			cs.setInt(3, SystemID);

			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				SystemID = t.getInt(1);
				System.out.print("Client ID : " + SystemID + " ");
				ClientLName = t.getString(2);
				System.out.println("ClientLName : " + ClientLName + " ");
				String ClientFname = t.getString(3);
				System.out.print("ClientFName :" + ClientFname + " ");
				int ZipCode = t.getInt(4);
				System.out.print("Zip Code :" + ZipCode + " ");
				String City = t.getString(5);
				System.out.print("City :" + City + " ");
				int Number = t.getInt(6);
				System.out.print("NO : " + Number + " ");
				String Street = t.getString(7);
				System.out.print("Street : " + Street + " ");
				int Phone = t.getInt(8);
				System.out.print("Phone :" + Phone + " ");
				String Email = t.getString(9);
				System.out.println("Eimail : " + Email);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void SearchCar() {

		java.sql.Date d = null;

		int VolcanoPriceMin;
		int VolcanoPriceMax;

		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.searchCar(?,?,?,?) }");

			System.out.println("Hmerominia : ");
			String date = sc.nextLine();
			d = Date.valueOf(date);
			cs.setDate(1, d);
			System.out.println("Dose VolcanoPrice min: ");
			VolcanoPriceMin = sc.nextInt();
			cs.setInt(2, VolcanoPriceMin);
			System.out.println("Dose VolcanoPrice max: ");
			VolcanoPriceMax = sc.nextInt();
			cs.setInt(3, VolcanoPriceMax);
			System.out.println("Dose Onoma Nisiou : ");
			String IslandName = sc.next();
			// String an = sc.next();
			cs.setString(4, IslandName);
			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				int CarID = t.getInt(1);
				System.out.print("CarID :" + CarID + " ");
				String type = t.getString(2);
				System.out.print("Type : " + type + " ");
				int Price = t.getInt(3);
				System.out.print("Price : " + Price + " ");
				int VolcanoPrice = t.getInt(3);
				System.out.print("VolcanoPrice : " + VolcanoPrice + " ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public static void InsertCastomer () {
		String ClientFName;
		String ClientLName;
		int SystemID;
		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.insertCustomer(?,?,?,?,?,?,?,?,?) }");
             
			System.out.print("Dose ID Pelati : ");
			int ClientID = sc.nextInt();
			cs.setInt(1,ClientID);
			System.out.println("Dose Epitheto Pelati : ");
			ClientFName = sc.next();
			cs.setString(2, ClientFName);
			System.out.println("Dose Onoma Pelati : ");
			ClientLName = sc.next();
			cs.setString(3, ClientLName);
			System.out.println("Dose Zip Code : ");
			int ZipCode = sc.nextInt();
			cs.setInt(4, ZipCode);
			System.out.print("Dose City");
			String City = sc.next();
			cs.setString(5, City);
			System.out.print("Dose NUM");
			int Num = sc.nextInt();
			cs.setInt(6, Num);
			System.out.print("Dose Street");
			String Street = sc.next();
			cs.setString(7, Street);
			System.out.print("Dose Phone");
			int Phone = sc.nextInt();
			cs.setInt(8, Phone);
			System.out.print("Dose Email");
			String Email = sc.next();
			cs.setString(9, Email);
			

			cs.execute();
			//ResultSet t = cs.executeQuery();

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	
	public static void UpdateCastomer () {
		String ClientFName;
		String ClientLName;
		int SystemID;
		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.updateCustomer(?,?,?,?,?,?,?,?,?) }");
             
			System.out.print("Dose ID Pelati : ");
			int ClientID = sc.nextInt();
			cs.setInt(1,ClientID);
			System.out.println("Dose Epitheto Pelati : ");
			ClientFName = sc.next();
			cs.setString(2, ClientFName);
			System.out.println("Dose Onoma Pelati : ");
			ClientLName = sc.next();
			cs.setString(3, ClientLName);
			System.out.println("Dose Zip Code : ");
			int ZipCode = sc.nextInt();
			cs.setInt(4, ZipCode);
			System.out.print("Dose City");
			String City = sc.next();
			cs.setString(5, City);
			System.out.print("Dose NUM");
			int Num = sc.nextInt();
			cs.setInt(6, Num);
			System.out.print("Dose Street");
			String Street = sc.next();
			cs.setString(7, Street);
			System.out.print("Dose Phone");
			int Phone = sc.nextInt();
			cs.setInt(8, Phone);
			System.out.print("Dose Email");
			String Email = sc.next();
			cs.setString(9, Email);
			

			cs.execute();
			//ResultSet t = cs.executeQuery();

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		
	}
	
	
	public static void CustomerReport () {
		java.sql.Date dmin = null;
		java.sql.Date dmax = null;
		
		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.customerreport(?,?) }");

			System.out.println("Hmerominia : ");
			String datemin = sc.nextLine();
			dmin = Date.valueOf(datemin);
			cs.setDate(1, dmin);
			System.out.println("Hmerominia : ");
			String datemax = sc.nextLine();
			dmin = Date.valueOf(datemax);
			cs.setDate(1, dmax);
			
			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				int SystemID = t.getInt(1);
				System.out.print("RoomID :" + SystemID + " ");
				
				String ClientLName = t.getString(2);
				System.out.println("ClientLName : " + ClientLName + " ");
				String ClientFname = t.getString(3);
				System.out.print("ClientFName :" + ClientFname + " ");
				String type = t.getString(4);
				System.out.print("HotelName : " + type + " ");
				int Roomid = t.getInt(5);
				System.out.print("Room ID : " +Roomid + " ");
				String RName = t.getString(6);
				System.out.print("Rental Name : " + RName + " ");
				int CarID = t.getInt(7);
				System.out.print("Car ID : " +CarID + " ");
				int ReservationNumber = t.getInt(8);
				System.out.print("Reservation ID : " +ReservationNumber + " ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	

	public static void SearchRoom() {

		java.sql.Date d = null;

		int VolcanoPriceMin;
		int VolcanoPriceMax;

		Scanner sc = new Scanner(System.in);
		try {
			CallableStatement cs = conn.prepareCall("{call dbo.searchRoom(?,?,?,?) }");

			System.out.println("Hmerominia : ");
			String date = sc.nextLine();
			d = Date.valueOf(date);
			cs.setDate(1, d);
			System.out.println("Dose VolcanoPrice min: ");
			VolcanoPriceMin = sc.nextInt();
			cs.setInt(2, VolcanoPriceMin);
			System.out.println("Dose VolcanoPrice max: ");
			VolcanoPriceMax = sc.nextInt();
			cs.setInt(3, VolcanoPriceMax);
			System.out.println("Dose Onoma Nisiou : ");
			String IslandName = sc.next();
			// String an = sc.next();
			cs.setString(4, IslandName);
			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				int RoomID = t.getInt(1);
				System.out.print("RoomID :" + RoomID + " ");
				String type = t.getString(2);
				System.out.print("Type : " + type + " ");
				int Price = t.getInt(3);
				System.out.print("Price : " + Price + " ");
				int VolcanoPrice = t.getInt(3);
				System.out.print("VolcanoPrice : " + VolcanoPrice + " ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void PriceRoom() {

		try {
			CallableStatement cs = conn.prepareCall("{call dbo.PriceRoom1() }");
			cs.execute();
			ResultSet t = cs.executeQuery();

			while (t.next()) {
				int roomid = t.getInt(1);
				System.out.print("Room ID = " + roomid + " ");
				String HotelName = t.getString(2);
				System.out.print("HotelName = " + HotelName + " ");
				int Price = t.getInt(3);
				System.out.println("Price = " + Price);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void PrintMenu() {
		System.out.println("----------Welcome To Menu--------");
		System.out.println("1) Price Room Report ");//Not Working
		System.out.println("2)  HotelDetails    ");//Working
		System.out.println("3)  ClientlDescr    " );//Working
		System.out.println("4)   SearchFlight   ");//Not
		System.out.println("5) SearchCar         ");//Not
		System.out.println("6) SearchRoom       ");//Not
        System.out.println("7)  WorkerSearch   ");//Working
        System.out.println("8)CustomerReport   ");// Not Working
        System.out.println("9)Insert Customer   ");
        System.out.println("10)Update Customer  ");
	}

	static void Menu() {
		boolean end = true;

		Scanner scanner = new Scanner(System.in);
		while (end != false) {
			System.out.println("Welcome to Volcano Travel Agency");
			System.out.println("IF YOUR AN EMPLOYEE PLEASE PRESS 1 TO CONNECT TO THE DATABASE");
			int answer = scanner.nextInt();

			System.out.println("USERNAME");
			String username = scanner.nextLine();
			username = scanner.nextLine();
			System.out.println(username);
//		String t = scanner.nextLine();
//	  while(!equals(username, "Panais\n")) {
//			System.out.println("Wrong Username");
//			System.out.println("USERNAME");
//			username = scanner.toString();
//		}
			System.out.println("Password");
			String pass = scanner.nextLine();
//	   pass = scanner.nextLine();
//	  while(equals(username, "Panais")) {
//			System.out.println("Wrong Pass");
//			System.out.println("Password");
//			username = scanner.toString();
//		}	

			System.out.println("Hello" + username + "Welcome To The Database!! ");
			PrintMenu();
			int choice = scanner.nextInt();
			if (choice == 1)
				PriceRoom();
			else if (choice == 2)
				HotelDetails();
			else if (choice == 3)
				ClientlDescr();
			else if (choice == 4)
				SearchFlight();
			else if (choice == 5)
				SearchCar();
			else if (choice == 6)
				SearchRoom();
			else if(choice ==7) WorkerSearch();
			else  if(choice==8)CustomerReport ();
			         else if(choice ==9) InsertCastomer();	
			         else if(choice ==10) UpdateCastomer();   
			         else
			             end = false;
 
		}

	}

	private static boolean equals(String username, String string) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {

		SimpleJDBC anObj = new SimpleJDBC();
		conn = anObj.getDBConnection();

		if (conn == null) {
			return;
		}
		System.out.println("WELCOME TO JDBC sample program ! \n\n");

		// keeps the main loop busy
//		anObj.insertClient();
//		anObj.insertWorkers();
//		anObj.insertHotel();
//		anObj.insertCarRent();
//		anObj.insertAirport();
//		anObj.insertAirlines();
//	   
//		anObj.insertFlights();
//		anObj.insertPayment();
//     	anObj.insertRoom();
//		 anObj.insertCars();
//		 anObj.insertReservation();
//		anObj.dropTables();
//		anObj.dropTables();
//		anObj.dropTables();
		 Menu();
   
		
		
		

		try {
			if (!conn.isClosed()) {
				System.out.print("Disconnecting from database...");
				conn.close();
				System.out.println("Done\n\nBye !");
			}
		} catch (Exception e) {
			// Ignore the error and continues
		}
	}

}