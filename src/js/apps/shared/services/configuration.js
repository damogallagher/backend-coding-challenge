var app = angular.module("alchemytec.config", []);

// Some environmental specific config options
var gulpEnvConfig = { /*{{gulp-env-config}}*/ };

app.constant("config", {
	expensesServiceApiRoot: gulpEnvConfig.expensesServiceApiRoot,
	vatServiceApiRoot: gulpEnvConfig.vatServiceApiRoot,
	servicesApiKey: gulpEnvConfig.servicesApiKey,
	staticRoot: gulpEnvConfig.staticRoot
});
