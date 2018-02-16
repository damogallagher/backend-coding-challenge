var app = angular.module("alchemytec.config", []);

// Some environmental specific config options
var gulpEnvConfig = { /*{{gulp-env-config}}*/ };

app.constant("config", {
	serviceApiRoot: gulpEnvConfig.serviceApiRoot,
	servicesApiKey: gulpEnvConfig.servicesApiKey,
	staticRoot: gulpEnvConfig.staticRoot
});
