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
| Solitaire | 🚧 Initial development |
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
│   ├── WarGame.java
│   ├── WarPlayer.java
│   └── WarRound.java
│
├── solitaire/
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
javac -d out src/**/*.java
```

### Run

```bash
java -cp out Main
```

> Ensure that `input.txt` is available in the expected location before running the program.

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

- Supports **2–8 players**
- Reads an ordered deck from `input.txt`
- Performs the specified perfect shuffle algorithm
- Evenly distributes cards among players
- Handles excess cards
- Players reveal their top card each round
- Highest-ranked card wins the round
- Winning player collects all played cards, placing the winning card on top of all cards played but placed at the bottom of the winning player's hand
- Game continues until one player owns all cards
- Link to specs: [War Card Game Specs](https://svicomph-my.sharepoint.com/:w:/g/personal/mlactaoen_svi_com_ph/IQBkXYVl-FbeTpr3sXhMpyQiAUk1XL2-M5A6yQbAcExp-rs?e=XExCBf&wdExp=TEAMS-TREATMENT&web=1&TeamsCID=e7b0533d-9d15-4564-9710-349089f3b02a&linkOpenTime=1784247721133)

### Solitaire

- Currently under development
- Will be implemented using the shared card game framework
- Link to specs: [Solitaire](https://svicomph-my.sharepoint.com/:w:/g/personal/mlactaoen_svi_com_ph/IQAN3Ad6vJnOT6so96L3U0VnAUxRdRejPBWxOOkTjuBBIQ0?e=CJYwdm&wdExp=TEAMS-TREATMENT&web=1&TeamsCID=b7a17a71-dc57-4464-a776-1660e5508203&linkOpenTime=1784249595940)

---

## To do

### War Card Game
- [ ] Additional code cleanup and refactoring
- [ ] Improve separation of game logic and presentation
- [ ] Add comprehensive JavaDoc comments

### Solitaire
- [ ] Implement Stock, Waste, Tableau, and Foundation piles
- [ ] Card movement validation
- [ ] Win-condition detection
- [ ] Progress detection
- [ ] User interaction loop

### General
- [ ] Improve project documentation
- [ ] Add unit tests
- [ ] Support additional shuffle algorithms
- [ ] Expand reusable framework for future card games