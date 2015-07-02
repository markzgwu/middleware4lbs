package me.projects.QP.methods;

import java.util.HashMap;

import me.projects.QP.methods.prediction.SessionUser;

public enum  SessionUserManager {
	INSTANCE;
	final HashMap<String,SessionUser> store = new HashMap<String,SessionUser>();
}
