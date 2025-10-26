![epicmediaeval_title.png](src/images/minecraft_title.png)
# Epic Mediaeval

**Epic Mediaeval** is a large-scale content and gameplay overhaul mod for Minecraft 1.21.3, built using the NeoForge modding platform. It transforms the vanilla experience into a deeply immersive medieval world, centered around survival, settlement building, social interaction, and kingdom management. From food and gear to dynamic NPC systems, Epic Mediaeval invites players to forge their own path in a living, breathing medieval realm.

The core concept of the mod revolves around **peasants**—a new intelligent entity type that forms the backbone of a player-controlled society. These NPCs can be found throughout the world, living in small, naturally generated huts. Players are given the tools and systems to interact with, command, and organize peasants into fully functional towns, complete with professions, roles, and responsibilities. Combined with new culinary, combat, and construction mechanics, Epic Mediaeval delivers a modular framework for building your own kingdom within the Minecraft sandbox.

---

## Features

### New Entity: Peasants

- **Peasants** are intelligent NPCs that generate across the Overworld alongside small medieval peasant huts.
- Each peasant is unique, with randomly generated names, appearance (skin texture and model type), and behavior patterns.
- Players may **subdue peasants** through multiple methods:
  - Offering valuable **gifts**
  - Completing specific **tasks**
  - **Intimidating** them through force or strategic combat
- Once subdued, peasants **pledge allegiance** to the player and can be recruited into their kingdom.

### Profession System

- Submissive peasants can be **assigned professions**, unlocking new AI behaviors and interactions:
  - **Farmer**: Plants, harvests, and stores crops.
  - **Cook**: Processes raw ingredients into complex medieval meals.
  - **Builder**: Constructs predefined structures using a blueprint-like placement system.
  - **Blacksmith**: Crafts weapons and armor for the player or local militia.
  - **Soldier**: Guards territory, patrols areas, and follows the player into combat.
- Professions are modular and support future extension through either gameplay progression or datapack integration.

### Command & Control

- Once under command, peasants respond to **player orders** based on context, proximity, and AI priority.
- The system supports both **direct commands** (e.g., follow me, attack target, build here) and **passive delegation** (e.g., assigned zones for farming, defense areas).
- Basic **task queues** and interaction cooldowns ensure balanced and realistic pacing in peasant behavior.

### Medieval Kingdom Building

- The mod allows players to construct **a self-sustaining medieval society** from scratch:
  - Recruit multiple peasants to serve different roles
  - Arm your soldiers, equip your blacksmiths, and establish your food supply chains
  - Design and build **medieval towns, castles, and outposts** with the help of builders and supply systems
- Eventually, players can form **organized armies** to defend their territory or explore the world as a military expedition.

### Food System Overhaul

- Adds **nearly 60 handcrafted medieval dishes**, each with unique textures, models, and crafting processes.
- Includes detailed ingredient chains: grains, meats, vegetables, alcohols, dairy, and herbs.
- New cooking stations, such as **stew stoves**, allow preparation of food through a **custom GUI recipe system**.
- Food not only restores hunger but may offer **temporary buffs** or **class-specific enhancements** (planned feature).

### Gear and Equipment

- Dozens of **new melee and ranged weapons** modeled after historical medieval arms:
  - Swords, axes, polearms, hammers, crossbows, and more
- A full lineup of **armor sets**, categorized by role and tier (e.g. militia gear, noble plate, scout leather)
- Gear integrates with the profession system: soldiers wear armor, blacksmiths can craft and repair it, etc.

---

## Known Issues / Warnings

Epic Mediaeval is currently in **active development**, and some features are partially implemented or pending refinement:

- Peasant AI navigation is basic and may occasionally fail in dense terrain, especially around multi-level structures.
- Some peasant professions (e.g., blacksmith and soldier) have limited functionality in the current build.
- Building placement does not currently flatten terrain or avoid obstacles intelligently; placement may require manual clearing.
- GUI interfaces for cooking and construction are functional but lack full JEI integration and tooltips.
- No tutorial or questing system is currently present—players must discover mechanics organically.
- Multiplayer behavior is experimental; peasant syncing and command propagation may desync under server load.

---

## Technical Highlights

While the mod is still expanding in scope, its foundation includes several advanced architectural choices to support long-term flexibility and performance:

- Utilizes **NeoForge's Data Component System** to synchronize dynamic properties such as name, texture path, and slim model state across client and server, reducing manual NBT handling.
- Employs **custom entity rendering pipeline**, including a modular `PeasantEntityRenderer` that dynamically binds texture and model variants at runtime.
- Defines entity behavior through **GoalSelector injection**, enabling clean separation of base pathfinding, profession logic, and player interaction logic.
- Incorporates a **custom task-based AI system** for peasants, structured to allow future task queueing, dependencies, and shared village memory (planned).
- Implements persistent equipment storage via `ItemStack.save(...)` with `RegistryAccess`, ensuring peasant gear is accurately retained across saves.
- Designed with **modular expansion** in mind:
  - Future support for village structures via JSON templates or data-driven schema
  - Configurable peasant behavior via datapacks or mod API
  - External mod compatibility for additional jobs, quest systems, or faction mechanics

---

Epic Mediaeval is intended as a platform for medieval fantasy experiences in Minecraft. Whether you're building a peaceful countryside village or leading an army into battle, the mod provides the tools, systems, and atmosphere to craft your own emergent story.
