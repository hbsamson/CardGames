# CardGames

A collection of terminal-based card games developed in **Java 25**. The project is designed with a modular architecture to maximize code reuse between different card games by sharing common components such as cards, decks, players, input/output handling, and shuffling strategies.

## Project Overview

This repository currently contains the following games:

- **War Card Game (WCG)** – A multiplayer card game where players compete by playing the highest-ranked card each round until one player acquires all cards.
- **Solitaire** – A single-player card game currently under development that reuses the shared card game framework.

The project emphasizes object-oriented design, modularity, and code reusability to simplify the addition of future card games.

---

## Features Completed

### Shared Framework
- Common `Card`, `Deck`, and `Player` models
- `Suit` and `Rank` enums
- Modular game architecture
- Console input/output utilities
- File reader for loading card decks
- Reusable shuffle interface and implementations

### War Card Game
- Read initial deck from `input.txt`
- Parse deck using `StringTokenizer`
- Perfect shuffle implementation
- Configurable player count (2–8)
- Configurable shuffle count
- Round-based gameplay
- Card distribution with excess card handling
- Highest-card determination
- Winner receives the winning card first
- Automatic game termination
- Round summaries
- Display each player's played card
- Display each player's deck
- Display winning player's deck after each round

---

## Current Project Status

| Component | Status |
|-----------|--------|
| Shared Framework | ✅ Completed |
| War Card Game | 🚧 Functional; ongoing refactoring and cleanup |
| Solitaire | 🚧 Functional; ongoing refactoring and cleanup |
| Documentation | 🚧 In Progress |

---

## Project Structure

```text
src/
│
├── core/
│   ├── Card.java
│   ├── Deck.java
│   ├── Game.java
│   ├── Player.java
│   ├── Rank.java
│   └── Suit.java
│
├── decks/
│   └── input.txt
│
├── io/
│   ├── ConsoleInput.java
│   └── DeckFileReader.java
│
├── shuffle/
│   ├── Shuffle.java
│   ├── RandomShuffle.java
│   └── PerfectShuffle.java
│
├── wcg/
│   ├── WarPlayer.java
│   ├── WarRound.java
│   └── WarGame.java
│
├── solitaire/
│   ├── FoundationPile.java
│   ├── TableauPile.java
│   ├── TalonPile.java
│   ├── WastePile.java
│   ├── SolitaireCard.java
│   ├── SolitaireMove.java
│   ├── SolitaireMoveType.java
│   ├── SolitaireTable.java
│   └── SolitaireGame.java
│
└── Main.java
```

---

## How to Compile and Run

### Requirements

- Java Development Kit (JDK) 25
- Terminal or IDE (IntelliJ IDEA recommended)

### Compile

```bash
javac --release 25 -d out $(find src -name "*.java")
```

### Run

```bash
java -cp out Main
```

> On a different machine, install JDK 25 and make sure your IDE or terminal is using that JDK. The project is written for Java 25, so using Java 8 or older will fail.

> Ensure that `input.txt` is available in the expected location before running the program.

---

## Current Displays

### War Card Game
```
=== Hello, welcome to Hannah's War Card Game ===
File path: src/decks/input.txt
Deck from src/decks/input.txt: 
[D-A, D-K, D-Q, D-J, D-10, D-9, D-8, D-7, D-6, D-5, D-4, D-3, D-2, H-A, H-K, H-Q, H-J, H-10, H-9, H-8, H-7, H-6, H-5, H-4, H-3, H-2, S-A, S-K, S-Q, S-J, S-10, S-9, S-8, S-7, S-6, S-5, S-4, S-3, S-2, C-A, C-K, C-Q, C-J, C-10, C-9, C-8, C-7, C-6, C-5, C-4, C-3, C-2]
Enter number of players (2-8): 2
Enter shuffle count (>0): 1

Shuffled Deck:
[D-A, S-A, D-K, S-K, D-Q, S-Q, D-J, S-J, D-10, S-10, D-9, S-9, D-8, S-8, D-7, S-7, D-6, S-6, D-5, S-5, D-4, S-4, D-3, S-3, D-2, S-2, H-A, C-A, H-K, C-K, H-Q, C-Q, H-J, C-J, H-10, C-10, H-9, C-9, H-8, C-8, H-7, C-7, H-6, C-6, H-5, C-5, H-4, C-4, H-3, C-3, H-2, C-2]

Player 1's Hand: [D-A, D-K, D-Q, D-J, D-10, D-9, D-8, D-7, D-6, D-5, D-4, D-3, D-2, H-A, H-K, H-Q, H-J, H-10, H-9, H-8, H-7, H-6, H-5, H-4, H-3, H-2]
Player 2's Hand: [S-A, S-K, S-Q, S-J, S-10, S-9, S-8, S-7, S-6, S-5, S-4, S-3, S-2, C-A, C-K, C-Q, C-J, C-10, C-9, C-8, C-7, C-6, C-5, C-4, C-3, C-2]

Round 1 Top Cards Played: [D-A, S-A]
Player 1's Cards in Hand: [D-K, D-Q, D-J, D-10, D-9, D-8, D-7, D-6, D-5, D-4, D-3, D-2, H-A, H-K, H-Q, H-J, H-10, H-9, H-8, H-7, H-6, H-5, H-4, H-3, H-2]
Player 2's Cards in Hand: [S-K, S-Q, S-J, S-10, S-9, S-8, S-7, S-6, S-5, S-4, S-3, S-2, C-A, C-K, C-Q, C-J, C-10, C-9, C-8, C-7, C-6, C-5, C-4, C-3, C-2]
Winner of Round 1: Player 1
Winner's deck (Player 1) - [D-K, D-Q, D-J, D-10, D-9, D-8, D-7, D-6, D-5, D-4, D-3, D-2, H-A, H-K, H-Q, H-J, H-10, H-9, H-8, H-7, H-6, H-5, H-4, H-3, H-2, D-A, S-A]

```

### Solitaire

```text
==================== Solitaire ====================
Foundation Zone
♠       ♣       ♥       ♦       
Empty   Empty   Empty   Empty   

Manoeuvre Tableau
---------------------------------------------------
D-A     XX      XX      XX      XX      XX      XX      
        D-7     XX      XX      XX      XX      XX      
                H-A     XX      XX      XX      XX      
                        H-9     XX      XX      XX      
                                H-5     XX      XX      
                                        H-2     XX      
                                                S-K     
---------------------------------------------------
Talon
[XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX, XX]
Talon Waste
[]
==================================================
```

---

## Development Roadmap

This project is designed to evolve from a terminal-based Java application into a full-stack web application while reusing the same core game logic throughout each stage.

### Level 1 – Console Application (Current)

The current implementation focuses on object-oriented design and reusable game logic.

```text
Console Input
      ↓
Game Logic
      ↓
Console Output
```

Current goals:

- Complete the War Card Game
- Implement Solitaire
- Reuse common card game components
- Improve modularity and maintainability
- Validate game behavior through testing

---

### Level 2 – JavaFX Desktop Application

A graphical user interface can be added without modifying the existing game logic.

```text
JavaFX
      ↓
Button Clicked
      ↓
WarGame.playRound()
      ↓
Update Screen
```

The JavaFX layer will only be responsible for:

- User interaction
- Displaying cards and player information
- Updating the interface after each action

The game logic remains inside reusable classes such as:

- `WarGame`
- `WarRound`
- `Card`
- `Deck`
- `Player`

Planned features:

- Game setup screen
- Player count selection
- Shuffle count selection
- Card graphics
- Round summaries
- Restart game functionality

---

### Level 3 – Spring Boot Backend

The reusable game logic will be integrated into a backend application.

Suggested project structure:

```text
backend/
├── controller/
├── service/
├── repository/
├── entity/
└── dto/
```

Responsibilities:

| Package | Responsibility |
|----------|----------------|
| `controller` | Handles REST API requests |
| `service` | Executes game logic |
| `repository` | Stores game sessions |
| `entity` | Database models |
| `dto` | Request and response objects |

Example REST endpoints:

```text
POST /games/start
POST /games/{id}/round
GET  /games/{id}
POST /games/{id}/save
POST /games/load
```

Supported operations:

- Start game
- Play round
- Save session
- Load session
- View current game state

---

### Level 4 – React Frontend

The React frontend communicates with the backend while keeping all game rules on the server.

```text
React
      ↓
REST API
      ↓
Spring Boot
      ↓
WarGame
```

The frontend will handle:

- Game setup
- Displaying cards
- Player information
- Round history
- User interaction

Possible components:

```text
GameSetup
GameBoard
PlayerPanel
CardDisplay
RoundControls
RoundResult
GameResult
```

Example flow:

```text
User clicks "Play Round"
        ↓
React sends API request
        ↓
Spring Boot executes WarGame.playRound()
        ↓
Updated game state returned
        ↓
React updates the interface
```

---

### Level 5 – Deployment

Deploy the application as a full-stack web application.

```text
React (Vercel)
        ↓
Spring Boot (Render / Railway)
        ↓
PostgreSQL
```

Suggested deployment stack:

| Layer | Technology |
|---------|------------|
| Frontend | React |
| Backend | Spring Boot |
| Database | PostgreSQL |
| Hosting | Vercel + Render/Railway |

At this stage, anyone can access the card games directly through a web browser.

Planned deployment features:

- Public web access
- Persistent game sessions
- Save and load functionality
- User accounts
- Multiple concurrent games
- Automatic deployment pipeline

---

## Long-Term Architecture

```text
React Frontend
        ↓
REST API
        ↓
Spring Boot
        ↓
Game Services
        ↓
Reusable Card Game Logic
        ↓
Database
```

The primary architectural goal is to keep the **game logic independent** of the user interface and persistence layer. This allows the same core implementation to be reused across multiple platforms, including:

- Console applications
- JavaFX desktop applications
- Spring Boot backends
- React web applications
- Automated tests
- Future card game implementations

---

## Game Specifications

### General Requirements

- Develop terminal-based card games using **Java 25**
- Utilize Java Collections Framework
- Read the initial deck from `input.txt`
- Parse the deck using **`StringTokenizer`**
- Use enums to represent card suits and ranks
- Follow an object-oriented and modular design

### War Card Game
[War Card Game Specifications](https://svicomph-my.sharepoint.com/:w:/g/personal/mlactaoen_svi_com_ph/IQBkXYVl-FbeTpr3sXhMpyQiAUk1XL2-M5A6yQbAcExp-rs?e=XExCBf&wdExp=TEAMS-TREATMENT&web=1&TeamsCID=e7b0533d-9d15-4564-9710-349089f3b02a&linkOpenTime=1784247721133)
- Supports **2–8 players**
- Reads an ordered deck from `input.txt`
- Performs the specified perfect shuffle algorithm
- Evenly distributes cards among players
- Handles excess cards
- Players reveal their top card each round
- Highest-ranked card wins the round
- Winning player collects all played cards, placing the winning card on top of all cards played but placed at the bottom of the winning player's hand
- Game continues until one player owns all cards

### Solitaire 
[Solitaire Specifications](https://svicomph-my.sharepoint.com/:w:/g/personal/mlactaoen_svi_com_ph/IQAN3Ad6vJnOT6so96L3U0VnAUxRdRejPBWxOOkTjuBBIQ0?e=CJYwdm&wdExp=TEAMS-TREATMENT&web=1&TeamsCID=b7a17a71-dc57-4464-a776-1660e5508203&linkOpenTime=1784249595940)
- Implements an automatic terminal-based Klondike Solitaire solver
- Uses seven tableau piles, four foundation piles, one talon, and one waste pile
- Deals the initial tableau using the ordered cards from `input.txt`
- Places all remaining cards into the talon
- Keeps tableau cards face down except for the exposed card of each pile
- Draws up to three cards from the talon at a time
- Builds foundation piles by suit in ascending rank order, from Ace to King
- Builds tableau piles in descending rank order with alternating colors
- Allows movement of a face-up tableau sequence
- Does not move cards from a foundation back to the tableau
- Searches for moves automatically from left to right
- Does not guarantee an optimal solution

#### Solitaire Move Priority

The automatic solver searches for moves in the following order:

1. Tableau → Foundation
2. Tableau → Tableau
3. Waste → Foundation
4. Waste → Tableau
5. Draw up to three cards from Talon → Waste
6. Recycle Waste → Talon
7. End the game when no further progress is possible

---

## To do

### War Card Game
- [X] Additional code cleanup and refactoring
- [ ] Improve separation of game logic and presentation
- [ ] Add comprehensive JavaDoc comments

### Solitaire
- [x] Create SolitaireCard
- [x] Create SolitaireTable
- [x] Create TableauPile
- [x] Create FoundationPile
- [x] Create TalonPile
- [x] Create WastePile
- [x] Deal the initial opening tableau
- [X] Display the table set of cards
- [X] Draw Stock → Waste
- [X] Recycle Waste → Stock
- [X] Implement Tableau validation
- [X] Implement Foundation validation
- [X] Implement move execution
- [X] Implement win detection
- [X] Implement no-progress or cycle detection

### General
- [ ] Improve project documentation
- [ ] Add unit tests
- [ ] Support additional shuffle algorithms
- [ ] Expand reusable framework for future card games

---

### Sources

[Solitaire Cards](https://opengameart.org/content/playing-cards-vector-png)