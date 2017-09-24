var game = {
	gameKey: null,
	userKey: null,
	board: null,
	isHost: false,
	hasGuest: false,
	ourTurn: false,
	winner: 0
}

var settings = {
	colors: {
		"host": "#e23f3f",
		"guest": "#268888",
		"empty": "#ececec"
	}
}

$(function() {
	for (var x = 0; x < 7; x++) {
		$("#board").append("<div class=\"col\" id=\"col" + x + "\"></div>")
		for (var y = 6; y > 0; y--) {
			var chara = getChara(x);
			$("#board > div#col" + x).append("<div class=\"cell empty\" id=\"cell_" + chara + y + "\"><div></div></div>");
		}
	}
	
	setInterval(function() {
		if (game.gameKey != null) {
			fetchGame()
		}
	}, 750)
	
	var key = 0;
	
	setInterval(function() {
		key++;
		var waiter;
		if (key % 4 == 0) {
			waiter = ""
		}
		if (key % 4 == 1) {
			waiter = "."
		}
		if (key % 4 == 2) {
			waiter = ".."
		}
		if (key % 4 == 3) {
			waiter = "..."
		}
		$("#waitLoader").html(waiter);
	}, 250)
	
	$(document).on('click', '#gameLink', function (event) {
		var elem = event.target;
		elem.setSelectionRange(0, elem.value.length)
		document.execCommand("copy");
	});
	
	$("#board > div").click(function(event) {
		console.log("t")
		place(parseInt(event.currentTarget.id.charAt(3)))
	});
	
	
	$("#joinForm").submit(function(event) {
		event.preventDefault()
		var parts = $("#joinText").val().split("/")
		var part = parts[parts.length-1]
		
		join(part)
	});
	
		
});

function create() {
	$("#preMenu").css("display", "none")
		
	api_createGame(function() {
		game.isHost = true;
		game.ourTurn = true;
		showWait()
	})
}

function join(key) {
	$("#preMenu").css("display", "none")
		
	api_joinGame(key, function() {
		api_fetchGame(function() {
			redrawBoard()
			$("#board").css("display", "block")
		})
	})
}

function showWait(callback) {
	$("#preMenu").css("display", "none")
	$("#preWait").css("display", "block")
	
	$("#gameLink").val("http://lorem.ipsum/join/" + game.gameKey)
}

function fetchGame(callback) {
	api_fetchGame(function() {
		redrawBoard()
		
		if (game.hasGuest) {
			if ($("#preWait").css("display") == "block") {
				$("#preWait").css("display", "none")
			}
			if ($("#board").css("display") == "none") {
				$("#board").css("display", "block")
			}
		}
	})
}

function place(col) {
	if (!game.ourTurn || game.winner != 0) {
		return;
	}
	game.ourTurn = false;
	
	for (var x = 0; x < game.board[col].length; x++) {
		if (game.board[col][x] == 0) {
			game.board[col][x] = game.isHost ? 2 : 1
			redrawBoard()
			
			api_place(col, function() {
				//
			})
			
			return 
		}
	}
}

function redrawBoard() {
	for (var c = 0; c < game.board.length; c++) {
		for (var r = 0; r < game.board[0].length; r++) {
			
			var color;
			switch (game.board[c][r]) {
				case 0:
					$("#cell_" + getChara(c) + (r+1)).addClass("empty")
					$("#cell_" + getChara(c) + (r+1)).removeClass("guest")
					$("#cell_" + getChara(c) + (r+1)).removeClass("host")
					break;
				case 1:
					$("#cell_" + getChara(c) + (r+1)).removeClass("empty")
					$("#cell_" + getChara(c) + (r+1)).addClass("guest")
					$("#cell_" + getChara(c) + (r+1)).removeClass("host")
					break;
				case 2:
					$("#cell_" + getChara(c) + (r+1)).removeClass("empty")
					$("#cell_" + getChara(c) + (r+1)).removeClass("guest")
					$("#cell_" + getChara(c) + (r+1)).addClass("host")
					break;
			}
		}
	}
}

function api_createGame(callback) {
	$.getJSON("http://localhost:8080/game/create", function(result) {
		game.gameKey = result.GameID;
		game.userKey = result.HostID;
		
		callback()
	});
}

function api_joinGame(key, callback) {
	$.getJSON("http://localhost:8080/game/join/" + key, function(result) {
		game.gameKey = key;
		game.userKey = result.GuestID;
		
		callback()
	});
}

function api_fetchGame(callback) {
	$.getJSON("http://localhost:8080/game/fetch/" + game.gameKey + "/" + game.userKey, function(result) {
		game.board = result.Cells;
		game.hasGuest = result.GuestPlayer;
		game.ourTurn = result.Turn;
		game.winner = result.Winner;
		
		console.log(result)
		
		callback()
	});
}

function api_place(col, callback) {
	$.getJSON("http://localhost:8080/game/place/" + game.gameKey + "/" + game.userKey + "/" + col, function(result) {
		callback()
	});
}

function getChara(x) {
	if (x == 0) {
		return 'a'
	} else if (x == 1) {
		return 'b'
	} else if (x == 2) {
		return 'c'
	} else if (x == 3) {
		return 'd'
	} else if (x == 4) {
		return 'e'
	} else if (x == 5) {
		return 'f'
	} else if (x == 6) {
		return 'g'
	}
}
