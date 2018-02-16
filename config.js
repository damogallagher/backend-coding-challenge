/******************************************************************************************

This will be copied to config.js by the CI Server as part of the test build process.

******************************************************************************************/

module.exports = {
	// Build target directory, this is where all the static files will end up
	target: "./static",
	htmltarget: "./static",

	// Font service url
	fonts: "//fast.fonts.net/jsapi/8f4aef36-1a46-44be-a573-99686bfcc33b.js",

	//application api key - in a real world system - we could get this in response to a user login
	servicesApiKey: "h3Nm6XackN2bJDSxZ3AFptKjil69cgPO",

	// The root directory for all expenses api calls
	expensesServiceApiRoot: "http://localhost:8090/expenses",

	// The root directory for all vat api calls
	vatServiceApiRoot: "http://localhost:8090/vat",

	// Root directory for static content
	staticRoot: "/"

};
