package Utilties;

public class Validation {

	public static boolean isValidID(int num) {

		String number = String.valueOf(num);
		try {
			System.out.println(number + " The Nubmer ");
			System.out.println((number.matches("[0-9]+") && num > 0) + " bbbb");
			return number.matches("[0-9]+") && num > 0;
		} catch (NumberFormatException e) {
			System.out.println(e + " Enter Correct Number Please ");
			Massege.displayMassage("Erorr :In Your ID", "Enter Correct Number Please");
			return false;
		}

	}

	public static boolean isString(String string) {
		try {
			return string.matches("[a-zA-Z]+");
		} catch (Exception e) {
			System.out.println(e + "Only Letter ");
			return false;
		}
	}

	public static boolean isPassword(String pass) {

		try {
			if (pass.length() == 0 || !pass.matches("[0-9A-Za-z]+")) {
				throw new Exception("PassWord Filed Not Valid");
			} else {
				return true;
			}

		} catch (Exception e) {
			System.out.println(e + " Enter Valid Password");
			Massege.displayMassage("Erorr in Passward", "Enter A Valid Password Number Please");

			return false;

		}
	}

	public static boolean isPhoneNumber(String pass) {

		try {
			if (pass.length() == 0 || !pass.matches("[0-9]+")) {
				throw new Exception("Phone Number Filed Not Valid");
			} else {
				return true;
			}

		} catch (Exception e) {
			System.out.println(e + " Enter a Valid Phone Number");
			Massege.displayMassage("Erorr in Passward", "Enter A Valid Phone Number Please");

			return false;

		}
	}

	public static boolean isName(String name) {
		try {
			System.out.println(name + " Inside Name");
			if (name.length() == 0 || name.matches("[^A-Za-z]"))
				throw new Exception("Name Is Not Valid");
			else
				return true;

		} catch (Exception e) {
			System.out.println(e + "Enter A Valid Name Please");
			Massege.displayMassage("Names", "Enter A Valid Name Please");
			return false;
		}
	}

}
