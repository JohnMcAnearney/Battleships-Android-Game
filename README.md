# Battleships-Android-Game
This was an android game my self and 3 others created for one of our 2nd year modules in University. This project lasted about 10 weeks alongside our other modules.

To run you must have android studio and an emulator or else an android device you can connect via usb. Run the application and play!

This was a 2nd year project and I was responsible for the code of the main board grid. From it being drawn, the calculating where the ships were on the board and snapping them into their correct square (This was done via a euclidean distance calculation where I would snap the ship to the closest square that the user had hovered the ship). I also coded an algorithm that would ensure ships were not placed on top of each other. If the ship was a ship already where the player wants to place their new ship then the algorithm would find the closest free space that fit the size of this ship (i.e. in battleships you have ships of size 2 squares to 5 squares). This was achieved via a search algorithm.
