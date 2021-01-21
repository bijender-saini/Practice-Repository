package com.saini.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.saini.entity.CityEntity;
import com.saini.entity.CountryEntity;
import com.saini.entity.StateEntity;
import com.saini.entity.UserEntity;
import com.saini.repository.CityRepository;
import com.saini.repository.CountryRepository;
import com.saini.repository.StateRepository;
import com.saini.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private JavaMailSender sender;

	public Map<Integer, String> findCountries() {

		List<CountryEntity> list = countryRepo.findAll();
		Map<Integer, String> map = new HashMap<Integer, String>();

		list.forEach(country -> {
			map.put(country.getCountryId(), country.getCountryName());
		});

		return map;
	}

	@Override
	public Map<Integer, String> findStates(Integer countryId) {

		List<StateEntity> list = stateRepo.findByCountryId(countryId);
		Map<Integer, String> map = new HashMap<Integer, String>();

		list.forEach(state -> {
			map.put(state.getStateId(), state.getStateName());
		});

		return map;

	}

	@Override
	public Map<Integer, String> findCities(Integer StateId) {
		List<CityEntity> list = cityRepo.findByStateId(StateId);
		Map<Integer, String> map = new HashMap<Integer, String>();

		list.forEach(city -> {
			map.put(city.getCityId(), city.getCityName());
		});

		return map;
	}

	@Override
	public boolean saveUser(UserEntity user) {

		user.setPassword(passwardGenerator());
		user.setAccStatus("LOCKED");
		UserEntity save = userRepo.save(user);

		String emailBody = getUnlockAccEmailBody(user);
		String subject = "UNLOCK Your Account | IES";

		boolean sendEmailUnlockAccount = sendEmailUnlockAccount(subject, emailBody, user.getEmail());

		return save.getUserId() != null && sendEmailUnlockAccount;
	}

	public String passwardGenerator() {

		SecureRandom random = new SecureRandom();
		String pwd = random.ints(48, 122 + 1).filter(i -> Character.isAlphabetic(i) || Character.isDigit(i)).limit(8)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return pwd;
	}

	@Override
	public boolean sendEmailUnlockAccount(String subject, String body, String to) {
		boolean send = false;

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			sender.send(message);
			send = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return send;
	}

	private String getUnlockAccEmailBody(UserEntity user) {
		String bodyLine = "";
		StringBuffer sb = new StringBuffer("");
		try {
			File f = new File("body.txt");
			FileReader fr = new FileReader(f);
			
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}

			bodyLine = sb.toString();

			bodyLine = bodyLine.replace("<FNAME>", user.getFirstName());
			bodyLine = bodyLine.replace("<LNAME>", user.getLastname());
			bodyLine = bodyLine.replace("<TEMP-PWD>", user.getPassword());
			bodyLine = bodyLine.replace("<EMAIL>", user.getEmail());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bodyLine;
	}

	@Override
	public boolean isUniqueEmail(String email) {
		UserEntity user = userRepo.findByEmail(email);

		return user == null;
	}

	// test case-1 : invalid email & pwd==>INVALID_CREDENTIALS
	// test case-2 : valid email & pwd & acc is LOCKED==>ACCOUNT LOCKED
	// test case-3 : valid email, pwd & acc Unlocked==>LOGIN_SUCCESS

	@Override
	public String checkLogin(String email, String password) {
		UserEntity user = userRepo.findByEmailAndPassword(email, password);
		if (user != null) {
			if (user.getAccStatus().equals("LOCKED")) {
				return " ACCOUNT_LOCKED";
			} else {
				return "Welcome" + user.getFirstName();
			}
		}

		return "invalid creadential";
	}

	// test case 1: User has given invalid temp-pwd==>false
	// test-case 2:User has Given Valid temp-pwd==>true
	@Override
	public boolean isTempPwdValid(String email, String tmpPwd) {
		UserEntity user = userRepo.findByEmailAndPassword(email, tmpPwd);

		return user != null;
	}

	@Override
	public String unlockAccount(String email, String newPwd) {

		UserEntity user = userRepo.findByEmail(email);

		user.setPassword(newPwd);
		user.setAccStatus("UNLOCK");
		try {
			userRepo.save(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Account Unlocked.Please Proceed with Login";
	}

	@Override
	public String forgetPassword(String email) {
		UserEntity user = userRepo.findByEmail(email);
		if (user != null && user.getAccStatus().equals("UNLOCK")) {
			String body = getForgetPwdEmailBody(user);
			String subject = "Us er Account Forget Password";
			if (sendEmailForgetPassward(subject, body, email))
				;
			{
				return "your Registered Email Password Successfully send to your Email-ID";
			}

		}
		return "Failed to Send Registered Email Password to  Email-ID ";
	}

	private String getForgetPwdEmailBody(UserEntity user) {
		String body = null;
		StringBuffer sb = new StringBuffer("");
		try {
			File f = new File("FogetPasswordBody.txt");
			boolean b = f.exists();
			FileReader fr = new FileReader(f);

			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}

			body = sb.toString();

			body = body.replace("<FNAME>", user.getFirstName());
			body = body.replace("<LASTNAME>", user.getLastname());
			body = body.replace("<EMAIL>", user.getEmail());
			body = body.replace("<PASSWORD>", user.getPassword());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return body;
	}

	@Override
	public boolean sendEmailForgetPassward(String subject, String body, String to) {

		boolean send = false;

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			sender.send(message);
			send = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return send;
	}

}
