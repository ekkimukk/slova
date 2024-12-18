const canvas = document.getElementById("game");
const context = canvas.getContext("2d");

canvas.width = 400;
canvas.height = 600;

// The player
const playerWidth = 70;
const playerHeight = 10;
const player = {
  x: canvas.width / 2 - playerWidth / 2,
  y: canvas.height - 20,
  width: playerWidth,
  height: playerHeight,
  color: "#fbf1c7",
  hitColor: "#cc241d",
  speed: 12,
};

// The falling blocks
const blockWidth = 20;
const blockHeight = 20;
let blocks = [];
let score = 0;
let highScore = getStoredHighScore();

// The special dangerous blocks
const dangerousBlockWidth = 10;
const dangerousBlockHeight = 30;
let dangerousBlocks = [];

// Pause status
let isPaused = false;

// Generate a random peaceful block
function generateBlock() {
  const x = Math.random() * (canvas.width - blockWidth);
  const block = {
    x: x,
    y: 0,
    width: blockWidth,
    height: blockHeight,
    color: getRandomColor(),
    speed: 2 + Math.floor(Math.random() * 3),
    // speed: 3,
  };
  blocks.push(block);
}

// Generate a dangerous block
function generateDangerousBlock() {
  const x = Math.random() * (canvas.width - dangerousBlockWidth);
  const dangerousBlock = {
    x: x,
    y: 0,
    width: dangerousBlockWidth,
    height: dangerousBlockHeight,
    color: "#9d0006",
    speed: 5,
  };
  dangerousBlocks.push(dangerousBlock);
}

// Color for the block
function getRandomColor() {
  let color = "#";
  //          red       green    yellow     blue     purple     aqua
  colors = ["fb4934", "b8bb26", "fabd2f", "83a598", "d3869b", "8ec07c"];
  return color + colors[Math.floor(Math.random() * 6)];
}

// Draw the player platform
function drawPlayer() {
  context.fillStyle = player.color;
  context.fillRect(player.x, player.y, player.width, player.height);
}

// Draw the falling blocks
function drawBlocks() {
  blocks.forEach((block) => {
    context.fillStyle = block.color;
    context.fillRect(block.x, block.y, block.width, block.height);
  });

  dangerousBlocks.forEach((dangerousBlock) => {
    context.fillStyle = dangerousBlock.color;
    context.fillRect(dangerousBlock.x, dangerousBlock.y, dangerousBlock.width, dangerousBlock.height);
  });
}

// Move the player platform
function movePlayer(direction) {
  if (direction === "left") {
    player.x -= player.speed;
    if (player.x < 0) {
      player.x = 0;
    }
  } else if (direction === "right") {
    player.x += player.speed;
    if (player.x + player.width > canvas.width) {
      player.x = canvas.width - player.width;
    }
  }
}

// Update the game state
function update() {
  context.clearRect(0, 0, canvas.width, canvas.height);

  if (!isPaused) {
    drawPlayer();
    drawBlocks();

    // Move the falling blocks
    blocks.forEach((block) => {
      block.y += block.speed;

      // Check a collision with the player platform
      if (
        block.y + block.height == player.y &&
        block.x >= player.x &&
        block.x + block.width <= player.x + player.width
      ) {
        // collisionAnimation();
        // blocks = blocks.filter((b) => b !== block);
        score++;
      } else if (
        block.y + block.height >= player.y && 
        block.x >= player.x &&
        block.x + block.width <= player.x + player.width
      ) {
        block.height /= 2;
      }

      // Remove blocks that reach the bottom
      if (block.y >= canvas.height) {
        blocks = blocks.filter((b) => b !== block);
      }

      // Check if the score has been updated
      if (score > highScore) {
        highScore = score;
        localStorage.setItem("highScore", highScore);
      }
    });

    // Move the falling dangerous blocks
    dangerousBlocks.forEach((dangerousBlock) => {
      dangerousBlock.y += dangerousBlock.speed;

      // Check a collision with the player platform
      if (
        dangerousBlock.y + dangerousBlock.height >= player.y &&
        dangerousBlock.x >= player.x &&
        dangerousBlock.x + dangerousBlock.width <= player.x + player.width
      ) {
        score = (score-10 < 0)?0:score-10;
        dangerousBlocks = dangerousBlocks.filter((db) => db !== dangerousBlock);
      }

      // Remove dangerous blocks that reach the bottom
      if (dangerousBlock.y >= canvas.height) {
        dangerousBlocks = dangerousBlocks.filter((db) => db !== dangerousBlock);
      }
    });

    // Generate a new block
    if (Math.random() < 0.02) {
      generateBlock();
    } else if (Math.random() < 0.005) {
      generateDangerousBlock();
    }
  }

  // Display the score
  context.fillStyle = "#fbf1c7";
  context.font = "20px Mononoki Nerd Font";
  context.fillText("Skora: " + score, 10, 30);
  context.fillText("Hátt stig: " + highScore, 10, 60);

  if (isPaused) {
    drawPauseScreen();
  }
}

// function changePlayerColor() {
//   context.fillStyle = player.hitColor;
//   context.fillRect(player.x, player.y, player.width, player.height);
// }

// Draw the pause screen
function drawPauseScreen() {
  context.fillStyle = "rgba(40, 40, 40, 0.5)";
  context.fillRect(0, 0, canvas.width, canvas.height);

  context.fillStyle = "#fbf1c7";
  context.font = "30px Mononoki Nerd Font";
  context.fillText("Gert hlé", canvas.width / 2 - 65, canvas.height / 2 - 15);

  context.font = "20px Mononoki Nerd Font";
  context.fillText("Ýttu á Rúm til að halda áfram", canvas.width / 2 - 160, canvas.height / 2 + 20);
  context.fillText("Ýttu á Enter til að endurræsa", canvas.width / 2 - 160 , canvas.height / 2 + 50);
}

// Toggle the pause state
function togglePause() {
  isPaused = !isPaused;
}

// Retrieve the high score from local storage
function getStoredHighScore() {
  const storedHighScore = localStorage.getItem("highScore");
  return storedHighScore ? parseInt(storedHighScore) : 0;
}

// Keyboard listener
document.addEventListener("keydown", (event) => {
  if (event.key === "ArrowLeft" || event.key === "h") {
    movePlayer("left");
  } else if (event.key === "ArrowRight" || event.key === "l") {
    movePlayer("right");
  } else if (event.key === " ") {
    togglePause();
  } else if (event.key === "r" || event.key === "R") {
    if (isPaused) {
      tooglePause();
    }
  } else if (event.key === "Enter") {
    if (isPaused) {
      // Restart the game
      if (score > highScore) {
        highScore = score;
        localStorage.setItem("highScore", highScore);
      }
      score = 0;
      player.x = canvas.width / 2 - playerWidth / 2;
      blocks = [];
      togglePause();
    }
  }
});

// Update the game every frame
function gameLoop() {
  update();
  requestAnimationFrame(gameLoop);
}

// Start the game
gameLoop();
