package JDBC;
//	package Products;

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
		 * Servlet implementation class GetProducts
		 */
		@WebServlet("/Product")
		public class Product extends HttpServlet {
		    private static final long serialVersionUID = 1L;

		    /**
		     * Default constructor.
		     */
		    public Product() {
		        // TODO Auto-generated constructor stub
		    }

		    /**
		     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
		     *      response)
		     */
		    protected void doGet(HttpServletRequest request, HttpServletResponse response)
		            throws ServletException, IOException {
		        response.setContentType("Product/html");
		        Connection con = null;

		        try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "root");


		            String searchId = request.getParameter("searchId");

		            PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE id=?");
		            ps.setString(1, searchId);

		            ResultSet rs = ps.executeQuery();
		            boolean found = false;
		            while (rs.next()) {
		                System.out.print("ID: " + rs.getString("id") + "\t");
		                System.out.print("Product Name: " + rs.getString("product_name") + "\t");
		                System.out.print("Category: " + rs.getString("category") + "\t");
		                System.out.print("Quantity: " + rs.getInt("quantity") + "\t");
		                System.out.println("Price: " + rs.getInt("price"));
		                found = true;
		            }
		            if (!found) {
		                System.out.println("No Product with the given ID");
		            }
		            con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } catch (ClassNotFoundException e) {
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
		        doGet(request, response);
		    }

}
