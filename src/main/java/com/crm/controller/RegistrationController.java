package com.crm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crm.users.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserDetailsManager userManager;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showRegistrationForm(Model theModel) {

		theModel.addAttribute("crmUser", new CrmUser());

		return "registration-form";

	}

	@PostMapping("/processRegistrationForm")
	public String processRegistration(@Valid @ModelAttribute("crmUser") CrmUser crmUser, BindingResult theBindingResult,
			Model model) {

		String userName = crmUser.getName();
		System.out.println("userName::" + userName);

		if (theBindingResult.hasErrors()) {

			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("registrationError", "User name/password can not be empty.");

			return "registration-form";

		}
		boolean userExist = doesUserExist(userName);
		if (userExist) {

			model.addAttribute("crmUser", new CrmUser());
			model.addAttribute("registrationError", "User name already exists");

			return "registration-form";
		}
		// encrypt the password
		String encodedpassword = passwordEncoder.encode(crmUser.getPassword());

		// add the encrypting algorithm
		encodedpassword = "{bcrypt}".concat(encodedpassword);
		
		//set the authorities as by default as employee
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");

		//create a new user object
		User user = new User(userName, encodedpassword, authorities);
		
		//save user in the database
		userManager.createUser(user);

		return "registration-confirmation";

	}

	private boolean doesUserExist(String userName) {

		boolean status = userManager.userExists(userName);
		System.out.println("doesUserExist>>" + userName + "  status>>" + status);

		return status;
	}
}
