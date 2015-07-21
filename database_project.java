Enter file contents here
package food_avengers;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *   @author NAEUN KIM
 * 
 *         Make overall outlook of this program. Connect database to show the
 *         restaurants that user chooses in second tab. User can input the
 *         location and menu type she or he wants.
 * 
 *         First tab named Team introduction, I upload the team image that I
 *         made. Second tab named Restaurant shows information of specific
 *         restaurants that depends on user's choice for location and menu type.
 *         Third tab named Menu show menu of the restaurant. In the fourth tab
 *         called Evaluation, the user can evaluate the restaurants in fourth
 *         tab. The Last Lab named InAPP coupon tab shows the coupons remaining. 
 *         When the coupon is outdated, the program deletes the coupon automatically.
 * 
 */

// ///////////////////////////////////////////////////////
// /////////////////////main class////////////////////////
// ///////////////////////////////////////////////////////

public class foodavengers extends JFrame implements Runnable {
	// //////////////////////////////////////////////////////
	// ///////define components for food avengers////////////
	// //////////////////////////////////////////////////////

	static final int WIDTH = 600;
	static final int HEIGHT = 900;
	static Connection con = null;
	
	// /////////////////Showing Image/////////////////////
	/**
	 * @author NAEUN KIM Using imgURL, the first tab shows the team image.
	 */
	URL imgURL = getClass().getResource("loadingimage.jpg");
	ImageIcon icon = new ImageIcon(imgURL);
	Image image = icon.getImage().getScaledInstance(900, 500,
			Image.SCALE_SMOOTH);
	JLabel imagelabel = new JLabel(new ImageIcon(image));
	JPanel imagepanel = new JPanel(new FlowLayout());

	// /////////////Showing Result of searching//////////////
	JTextArea textarea1 = new JTextArea(50, 50);
	JTextArea textarea3 = new JTextArea(50, 50);
	JTextArea textarea4 = new JTextArea(50, 50);
	JPanel textareapanel = new JPanel(new FlowLayout());
	JTextArea textarea5 = new JTextArea(50, 50);

	// ///////////////////Searching field////////////////////
	JTabbedPane tabbedPane = new JTabbedPane();
	JLabel jlblName1 = new JLabel("Location:  ");
	JLabel jlblName2 = new JLabel("Menu Type: ");

	JLabel rest_name = new JLabel("Restaurant name: ");
	JLabel restaurant = new JLabel("Restaurant name: ");
	JLabel dbcourse_restaurant = new JLabel("Restaurant Name: ");

	// /////////////To contain Textarea of Result///////////
	JPanel searchlist = new JPanel(new FlowLayout());
	JPanel searchlist2 = new JPanel();
	JPanel searchlist_menuTab = new JPanel(new FlowLayout());
	JPanel searchlist4 = new JPanel(new FlowLayout());

	// ////////////////Button for Searching/////////////////
	JButton tab1_searchButton = new JButton("Search");
	JButton tab2_searchButton = new JButton("Search");
	JButton tab3_searchButton = new JButton("Search");
	JButton tab4_searchButton = new JButton("Search");

	// ////////////////TextField for Result//////////////////
	JTextField textfield1 = new JTextField(10);
	JTextField textfield2 = new JTextField(10);
	JTextField textfield3 = new JTextField(10);
	JTextField textfield4 = new JTextField(10);

	// ////////////////Calendar For Coupon////////////////////
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH) + 1;
	int day = c.get(Calendar.DAY_OF_MONTH);

	boolean execution = true;

	public void getfromresultset(ResultSet rs) {
		try {
			while (rs.next()) {
				textarea1.append(rs.getString("name") + "	");
				textarea1.append(rs.getString("type") + "	");
				textarea1.append(rs.getString("capacity") + "	");
				textarea1.append(rs.getString("mood") + "	\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getFromResultSet3(ResultSet rs) {
		try {
			while (rs.next()) {
				textarea3.append(rs.getString("name") + " ");
				textarea3.append(rs.getString("menu_type") + " ");
				textarea3.append(rs.getString("menu_name") + " ");
				textarea3.append(rs.getString("price") + " \n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getfromresultset4(ResultSet rs) {
		try {
			while (rs.next()) {
				textarea4.append(rs.getString("name") + "	");
				textarea4.append(rs.getString("evaluation") + "	\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getfromresult5(ResultSet rs) {
		try {
			while (rs.next()) {
				textarea5.append(rs.getString("name") + "	");
				textarea5.append(rs.getString("type") + "	");
				textarea5.append(rs.getString("coupon_name") + "	");
				textarea5.append(rs.getString("percentage") + "	");
				textarea5.append(rs.getString("enddate") + "	\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void del_tuple() throws SQLException {
		ResultSet rs = null;
		PreparedStatement pState;
		pState = con.prepareStatement("delete from dbcourse_inapp_coupon"
				+ " where ? >= extract(year from enddate)"
				+ "and ? >= extract(month from enddate)"
				+ "and ? > extract(day from enddate)");
		pState.setString(1, "" + year);
		pState.setString(2, "0" + month);
		pState.setString(3, "" + day);
		pState.executeUpdate();
	}

	ResultSet coupon_now() {
		ResultSet rs = null;
		PreparedStatement pState;
		// compare with current date
		try {
			del_tuple();
			pState = con
					.prepareStatement("select name, type,coupon_name, percentage, enddate "
							+ "from DBCOURSE_restaurant natural join DBCOURSE_inapp_coupon "
							+ "order by enddate asc");
			rs = pState.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	ResultSet menu_location(String place, String menu) {
		ResultSet rs = null;
		PreparedStatement pState;
		if (place.length() != 0 && menu.length() != 0) {
			try {
				pState = con
						.prepareStatement("select name, type, capacity, mood"
								+ " from dbcourse_listed_restaurant" 
								+ " where type = ? and location = ?");

				pState.setString(1, menu);
				pState.setString(2, place);

				rs = pState.executeQuery();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (place.length() == 0 && menu.length() == 0) {
			try {
				Statement query = con.createStatement();
				query.executeQuery("select name, type, capacity, mood from dbcourse_listed_restaurant");
				rs = query.getResultSet();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rs;
	}

	public ResultSet menu(String menu) {
		PreparedStatement pState;
		ResultSet rs = null;
		try {	
				pState = con
				.prepareStatement("select name, type, capacity, mood "
						+ "from dbcourse_listed_restaurant"   
						+ " where type = ?");		      
			pState.setString(1, menu);
			rs = pState.executeQuery();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return rs;
	}

	public ResultSet location(String place) {
		PreparedStatement pState;
		ResultSet rs = null;
		try {
			pState = con
					.prepareStatement("select name, type, capacity,mood"
							+ "from dbcourse_listed_restaurant"
							+ " where location = ? ");  	   
			pState.setString(1, place);
			rs = pState.executeQuery();
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
		return rs;
	}

	
	public ResultSet detailedMenu(String res_name) {
		PreparedStatement pState;
		ResultSet rs = null;
		try {
			pState = con
					.prepareStatement("select name, menu_type, menu_name, price"
							+ " from dbcourse_restaurant natural join dbcourse_menu"
							+ " where name = ? order by menu_type"); 
			pState.setString(1, res_name);
			rs = pState.executeQuery();
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
		return rs;

	}
	public void insert_tuple() throws SQLException {
		PreparedStatement pState = null;
		try {
			pState = con.prepareStatement("insert into dbcourse_menu "
					+ " (01, 'appetizer', 'soup of the day', 13000)");
			pState.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
		}
	}
	
	public void update_tuple() throws SQLException {
		PreparedStatement pState;
		try {
			pState = con.prepareStatement("update dbcourse_menu"
					+ " set price = price*1.01"
					+ " where ? >= extract(year from enddate)");

			pState.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("SQLState: " + sqle.getSQLState());
		}
	}

	ResultSet evaluation(String res_name) {
		ResultSet rs = null;
		PreparedStatement pState;
		if (res_name.length() != 0) {
			try {
				pState = con
						.prepareStatement("select name, evaluation"
								+ " from dbcourse_final_evaluation"
								+ " where name = ?");

				pState.setString(1, res_name);
				rs = pState.executeQuery();
			} catch (SQLException sqex) {
				System.out.println("SQLException: " + sqex.getMessage());
				System.out.println("SQLState: " + sqex.getSQLState());
			}
		}
		return rs;
	}

	public foodavengers(String s) throws SQLException {
		super(s);
		this.setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
		searchlist.add(jlblName1);
		searchlist.add(textfield1);
		searchlist.add(jlblName2);
		searchlist.add(textfield2);
		searchlist.add(tab1_searchButton);
		add(searchlist, BorderLayout.NORTH);

		// ///////////////////first tab/////////////////////
		imagepanel.add(imagelabel);
		tabbedPane.addTab("Team Introduction", null, imagepanel);

		// ///////////////////second tab/////////////////////
		JPanel secondtab = new JPanel();
		tabbedPane.addTab("Restaurant", null, searchlist2);
		searchlist2.setLayout(new FlowLayout());
		searchlist2.add(textarea1);

		textarea1.setBackground(getBackground());
		textarea1.append("★★★★★★Restaurant Names that you want!★★★★★★" + "\n\n");
		textarea1.append("|Name	|type	|capacity	|mood	| " + "\n\n");
		
		JPanel thirdtab = new JPanel();
		tabbedPane.addTab("Menu", null, thirdtab);
		thirdtab.setBackground(getBackground());
		thirdtab.setLayout(new BorderLayout());
		thirdtab.add(searchlist_menuTab, BorderLayout.NORTH);
		searchlist_menuTab.add(dbcourse_restaurant);
		searchlist_menuTab.add(textfield3);
		searchlist_menuTab.add(tab3_searchButton);

		textarea3.setBackground(getBackground());
		textarea3.append("|Name |Menu Type |Menu Name |Price" + "\n\n");

		JPanel fourthtab = new JPanel();
		fourthtab.setBackground(getBackground());
		fourthtab.setLayout(new BorderLayout());
		fourthtab.add(searchlist4, BorderLayout.NORTH);
		textareapanel.add(textarea4);
		fourthtab.add(textareapanel, BorderLayout.CENTER);
		searchlist4.add(rest_name);
		searchlist4.add(textfield4);
		searchlist4.add(tab4_searchButton);
		tabbedPane.addTab("Evaluation", null, fourthtab);

		textarea4.setBackground(getBackground());
		textarea4.append("★★★★★★Evaluation of Restaurant that you search!★★★★★★"
				+ "\n\n");
		textarea4.append("|Name	|evaluation	| " + "\n\n");
	
		JPanel fifthtab = new JPanel();
		tabbedPane.addTab("InApp Coupon", null, fifthtab);
		fifthtab.setLayout(new FlowLayout());
		fifthtab.setBackground(getBackground());
		fifthtab.add(textarea5);
		textarea5.setBackground(getBackground());
		textarea5.append("★★★★★Coupons you have!★★★★★★" + "\n\n");
		textarea5.append("|Name       	|Type	  |Coupon Name 	|Percentage 	  |End date 	  | "
				+ "\n\n");
		getfromresult5(coupon_now());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		tab1_searchButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {

				String location1 = textfield1.getText();
				String menu = textfield2.getText();

				if (location1.length() != 0 && menu.length() != 0)
					getfromresultset(menu_location(location1, menu));
				else if (location1.length() == 0 && menu.length() == 0)
					getfromresultset(menu_location(location1, menu));
				else if (location1.length() != 0 && menu.length() == 0)
					getfromresultset(location(location1));
				else if (location1.length() == 0 && menu.length() != 0)
					getfromresultset(menu(menu));

			}
		});

		tab3_searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String resname3 = textfield3.getText();
				if (resname3.length() != 0)
					getFromResultSet3(detailedMenu(resname3));

				JDesktopPane desktopPane = new JDesktopPane();
				JInternalFrame intFrame = new JInternalFrame(
						"★★★★★★Menu of the Selected Restaurant!★★★★★★");
				intFrame.setMaximizable(true);
				intFrame.setIconifiable(true);
				intFrame.setResizable(true);
				intFrame.setClosable(true);
				intFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
				intFrame.setSize(500, 300);
				intFrame.setVisible(true);
				intFrame.setLocation(95, 80);
				intFrame.add(textarea3);

				desktopPane.add(intFrame);
				thirdtab.add(desktopPane, BorderLayout.CENTER);
			}
		});

		tab4_searchButton.addActionListener(new ActionListener() {
			/**
			 * For Action events, use ActionListener When tab4_searchbutton is
			 * clicked, actionPerformed is called and executed.
			 */
			public void actionPerformed(ActionEvent e) {
				/**
				 * This is Anonymous inner listener with argument ActionEvent e
				 */
				String resname4 = textfield4.getText();
				if (resname4.length() != 0)
					getfromresultset4(evaluation(resname4));
			}
		});
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args){
		 String path = foodavengers.class.getResource("").getPath();
		 FileReader file = null;
		 try {
			file = new FileReader(path + "properties.txt");
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
			String url = null, username = null, password = null;
			try {
				  BufferedReader br = new BufferedReader(file); 
				   url=br.readLine();
				   username=br.readLine();
				   password=br.readLine();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 	
	
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			con = DriverManager.getConnection(url, username, password); 
			  Statement query = con.createStatement();
			  query.executeQuery("USE dbproject");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 foodavengers screen;
		try {
			screen = new foodavengers("Food Avengers");
			screen.setSize(foodavengers.WIDTH, foodavengers.HEIGHT);
			screen.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
