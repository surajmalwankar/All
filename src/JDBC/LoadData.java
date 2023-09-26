package JDBC;

import java.sql.PreparedStatement;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.io.IOException;
		import java.sql.Connection;
		import java.sql.DriverManager;
		import java.sql.PreparedStatement;
		import java.sql.ResultSet;
		import java.sql.SQLException;
		import java.sql.Statement;

		import javax.servlet.ServletException;
		import javax.servlet.annotation.WebServlet;
		import javax.servlet.http.HttpServlet;
		import javax.servlet.http.HttpServletRequest;
		import javax.servlet.http.HttpServletResponse;

		/**
		 * Servlet implementation class LoadData
		 */
		@WebServlet("/loadData")
		public class LoadData extends HttpServlet {
			private static final long serialVersionUID = 1L;

			/**
			 * @see HttpServlet#HttpServlet()
			 */
			public LoadData() {
				super();
				// TODO Auto-generated constructor stub
			}

			/**
			 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
			 *      response)
			 */
			protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				// TODO Auto-generated method stub
				System.out.println("Data loading...");
				response.setContentType("Product/html");
				Connection con = null;

				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "root");

					// Create the "product" table if it doesn't exist
					createProductTable(con);

					// Insert three sample product entries
					insertProductData(con);
					con.close();
					
					System.out.println("Data loaded in the database successfully");
				} catch (SQLException e) {
					System.out.println("Data load unsuccessful");
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					System.out.println("Data load unsuccessful");
					e.printStackTrace();
				}

				response.sendRedirect("Product.html");
			}

			/**
			 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
			 *      response)
			 */
			protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
				// TODO Auto-generated method stub
				doGet(request, response);
			}

			// Create the "product" table if it doesn't exist
			private void createProductTable(Connection con) throws SQLException {
				String dropTableSQL = "DROP TABLE IF EXISTS product";
				String createTableSQL = "CREATE TABLE IF NOT EXISTS product (" + "id INT AUTO_INCREMENT PRIMARY KEY, "
						+ "product_name VARCHAR(20) NOT NULL unique, " + "category VARCHAR(20) NOT NULL, "
						+ "quantity INT NOT NULL, " + "price INT NOT NULL)";
				Statement statement = con.createStatement();
				statement.executeUpdate(dropTableSQL);
				statement.execute(createTableSQL);
			}

			// Insert three sample product entries
			private void insertProductData(Connection con) throws SQLException {
				String insertDataSQL = "INSERT INTO product (product_name, category, quantity, price) VALUES (?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(insertDataSQL);

				// Product 1
				ps.setString(1, "Product 1");
				ps.setString(2, "Category A");
				ps.setInt(3, 10);
				ps.setInt(4, 100);
				ps.executeUpdate();

				// Product 2
				ps.setString(1, "Product 2");
				ps.setString(2, "Category B");
				ps.setInt(3, 5);
				ps.setInt(4, 50);
				ps.executeUpdate();

				// Product 3
				ps.setString(1, "Product 3");
				ps.setString(2, "Category A");
				ps.setInt(3, 20);
				ps.setInt(4, 150);
				ps.executeUpdate();	
		
	}

}
