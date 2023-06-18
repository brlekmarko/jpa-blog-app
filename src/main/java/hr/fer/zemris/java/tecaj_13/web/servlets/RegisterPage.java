package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/register")
public class RegisterPage extends HttpServlet{

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// korisnik je trebao poslati ove podatke
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String nick = req.getParameter("nick");
		String password = req.getParameter("pass");
		
		// setamo atribute da ih mozemo staviti kao pocetne vrijednosti na ponovnom ucitavanju
		req.setAttribute("firstName", firstName);
		req.setAttribute("lastName", lastName);
		req.setAttribute("email", email);
		req.setAttribute("nick", nick);

		if (firstName == null || lastName == null || email == null || nick == null || password == null) {
			req.setAttribute("message", "All fields must be provided.");
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}

		String hashedPassword = null;
		try{
			hashedPassword = new String(MessageDigest.getInstance("SHA-1").digest(password.getBytes("UTF-8")), "UTF-8");
		} catch (Exception e) {
			req.setAttribute("message", "Error while hashing password.");
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}

		// pokusavamo dohvatiti korisnika s tim nickom da vidimo dal vec postoji
		BlogUser user = null;
		try {
			user = DAOProvider.getDAO().getBlogUser(nick);
		} catch (Exception e) {
			req.setAttribute("message", "Error in database.");
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}

		if (user != null) {
			req.setAttribute("message", "Nick already taken.");
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}
		
		// probamo registrirati korisnika
		try {
			user = DAOProvider.getDAO().saveBlogUser(new BlogUser(firstName, lastName, nick, email, hashedPassword));
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "Error while registering.");
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}
		
		//postavimo podatke u session
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getSession().setAttribute("current.user.email", user.getEmail());

		resp.sendRedirect("main");
		
	}
}
