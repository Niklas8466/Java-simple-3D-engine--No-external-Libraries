# My third try at creating a 3D renderer
This project took roughly 5 weeks. I made everything from sratch with no external libraries to understand how 3d engines work.
**No AI was used to write this code**
**3D Models aren't made by me**
Here are some pictures of the finished project:
<img width="1415" height="889" alt="image" src="https://github.com/user-attachments/assets/6d1f7ec3-19a5-4383-8f1d-94dbc9eea1a0" />
![duck](https://github.com/user-attachments/assets/0db92f3d-40de-4183-96cb-4bf7185017b5)
![pikatchu](https://github.com/user-attachments/assets/6b28a38a-8718-423d-b105-1975f1459861)


## Features:
- Cubes, Pyramides, Spheres
- Camera movement
- Camera rotation
- Backface culling
- Culling of negativ z-values
- Painter's algorithm
- Filled shapes
- Shape outlines
- 3D Models

## How does it work?
1. calculate vertices relativ to **object**
2. calculate vertices relativ to the **origin**
3. calculate vertices relativ to **camera**
4. project vertices on the screen
5. connect the vertices to form triangles
6. cut triangles, which aren't visible (backface culling and culling negativ z-values)
7. sort the triangles back to front (painter's algorithm)
8. draw the trinangles in order

## known bugs
- renderer messes up the triangle order with multiple objects too close to each other
- everything disapears if the scene is too complex

## conclusion
The main issues are the painter's algorithm and the speed of the renderer. In order to fix this, modern engines use **rasterisation** and even more optimisations like combining multiple steps into one matrix.
The focus of this project was to learn, how all of this works...mission accomplished ;)
