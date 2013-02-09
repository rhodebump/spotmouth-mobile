/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */

/**
 * Constructor
 */
function FBConnect()
{

}

/**
 * Display a new browser with the specified URL.
 * 
 * NOTE: If usePhoneGap is set, only trusted PhoneGap URLs should be loaded,
 *       since any PhoneGap API can be called by the loaded HTML page.
 *
 * @param url           The url to load
 * @param usePhoneGap   Load url in PhoneGap webview [optional] - Default: false
 */
//FacebookLogin.prototype.login = function(appId) {
FBConnect.prototype.connect = function(client_id,redirect_uri,display) {
    PhoneGap.exec(null, null, "FBConnect", "connect", [client_id]);
    //this will not call the locationchange, this is handled by the plugin internally, and the plugin
    //will call the this.onConnect() callback that you would need to set
    
};

FBConnect.prototype.logout = function(client_id) {
    PhoneGap.exec(null, null, "FBConnect", "logout", [client_id]);
};



/**
 * Load ChildBrowser
 */
PhoneGap.addConstructor(function() {
    PhoneGap.addPlugin("FBConnect", new FBConnect());
    PluginManager.addService("FBConnect", "com.phonegap.plugins.facebook.FBConnect");
});

FBConnect.prototype.onLocationChange = function(thetoken)
{
	this.accessToken = thetoken;
	this.onConnect();
}

FBConnect._onLocationChange = function(newLoc)
{
	window.plugins.fbConnect.onLocationChange(newLoc);
}



FBConnect.install = function()
{
	if(!window.plugins)
	{
		window.plugins = {};	
	}
	window.plugins.fbConnect = new FBConnect();
	return window.plugins.fbConnect;
}


