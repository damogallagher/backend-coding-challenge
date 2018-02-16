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

      //Check if a currency is set with the expense
			var valueArr = $scope.newExpense.total_value.split(" ");
			if (valueArr.length > 1) {
				$scope.newExpense.original_currency = valueArr[1];
			}
			//Set the total value just to be the value part
			$scope.newExpense.total_value = valueArr[0];
			// Post the expense via REST
			restExpenses.post($scope.newExpense).then(function(data) {
					// Reload new expenses list
					loadExpenses();
					$scope.clearExpense();
			});
		}
	};

  //Method to get the vat total only if we have a number
  $scope.getTotalVat = function() {
		//get just the value part - incase a currecny is also specified
		var valueArr = $scope.newExpense.total_value.split(" ");
		var tempTotalValue = valueArr[0];

		if (isNaN(tempTotalValue)) {
			//console.log("the value " +$scope.newExpense.total_value+" is not a number");
			$scope.totalVat = 0.0;
			return
		}
		//call out to rest service to get the vat
		restVat.post(tempTotalValue).then(function(vatData) {
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
