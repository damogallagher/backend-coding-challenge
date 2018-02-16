"use strict";

/******************************************************************************************

Expenses controller

******************************************************************************************/

var app = angular.module("expenses.controller", ['ui.date']);

app.controller("ctrlExpenses", ["$rootScope", "$scope", "config", "restalchemy", function ExpensesCtrl($rootScope, $scope, $config, $restalchemy) {
	// Update the headings
	$rootScope.mainTitle = "Expenses";
	$rootScope.mainHeading = "Expenses";

	// Update the tab sections
	$rootScope.selectTabSection("expenses", 0);

	//Set up an object to store the header information for authentication
	var servicesApiKey = $config.servicesApiKey;
  var apiKeyHeader = {"x-api-key" : servicesApiKey}

	var restExpenses = $restalchemy.init({ root: $config.expensesServiceApiRoot, headers: apiKeyHeader }).at("expenses");
	var restVat = $restalchemy.init({ root: $config.vatServiceApiRoot, headers: apiKeyHeader }).at("vat");

	$scope.dateOptions = {
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy"
	};

	var loadExpenses = function() {
		// Retrieve a list of expenses via REST
		restExpenses.get().then(function(expenses) {
			$scope.expenses = expenses;
		});
	}

	//Function to format the date based on the output from the jquery library into the format we want
	function formatDate(date) {
	  var day = date.getDate();
	  var month = date.getMonth() + 1;
	  var year = date.getFullYear();

	  return day + '/' + month + '/' + year;
	}

	$scope.saveExpense = function() {
		if ($scope.expensesform.$valid) {
			var formattedDate = formatDate($scope.newExpense.date);
			$scope.newExpense.date = formattedDate;
			// Post the expense via REST
			restExpenses.post($scope.newExpense.total_value).then(function(data) {
					// Reload new expenses list
					loadExpenses();
					$scope.clearExpense();
			});
		}
	};

  //Method to get the vat total
  $scope.getTotalVat = function() {
		console.log("in getVatRate");
		restVat.post().then(function(vatData) {
			console.log(" vatData:"+vatData);
			$scope.totalVat = vatData;
		});
	}

	$scope.clearExpense = function() {
		$scope.newExpense = {};
		$scope.totalVat = 0.0;
	};

	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
}]);
