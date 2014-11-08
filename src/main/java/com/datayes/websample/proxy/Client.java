package com.datayes.websample.proxy;

public class Client {

	public static void main(String[] args) {
		BusinessHandler businessHandler = new BusinessHandler();
		UserManager userManager = (UserManager) businessHandler.newProxyInstance(new UserManagerImpl());
		String name = userManager.test("0001");
		System.out.println("Client.main() --- " + name);
	}

}
