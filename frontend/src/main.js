const roadGeometry = new THREE.BoxGeometry(15, 15, 0.2); // Cieńsza droga
const roadMaterial = new THREE.MeshStandardMaterial({
  color: "#2E2E2E", // Ciemniejszy kolor asfaltu
  roughness: 0.9, // Szorstka powierzchnia
  metalness: 0.1, // Lekki połysk
});
const road = new THREE.Mesh(roadGeometry, roadMaterial);
road.rotation.x = -Math.PI / 2;

scene.add(road);

const dirtGeometry = new THREE.BoxGeometry(15, 15, 0.6);
const dirtMaterial = new THREE.MeshStandardMaterial({
  color: "rgb(182, 182, 182)",
});
const dirt = new THREE.Mesh(dirtGeometry, dirtMaterial);
dirt.rotation.x = -Math.PI / 2;
dirt.position.y -= 0.4;
scene.add(dirt);

function addGrass(x, z) {
  const grassGeometry = new THREE.BoxGeometry(5, 5, 0.1);
  const grassMaterial = new THREE.MeshStandardMaterial({
    color: "rgb(21, 65, 3)",
  });
  const grass = new THREE.Mesh(grassGeometry, grassMaterial);
  grass.rotation.x = -Math.PI / 2;
  grass.position.y += 0.1;
  grass.position.z -= z;
  grass.position.x -= x;
  scene.add(grass);
}

function addZones(x, z, sizeX, sizeY) {
  const grassGeometry = new THREE.BoxGeometry(sizeX, sizeY, 0.1);
  const grassMaterial = new THREE.MeshStandardMaterial({
    color: "rgba(237, 237, 237, 0.8)",
  });
  const grass = new THREE.Mesh(grassGeometry, grassMaterial);
  grass.rotation.x = -Math.PI / 2;
  grass.position.y += 0.1;
  grass.position.z -= z;
  grass.position.x -= x;
  scene.add(grass);
}

// === Inicjalizacja pojazdów ===
const vehicles = [];
function createVehicle(x, z) {
  const geometry = new THREE.BoxGeometry(0.5, 0.5, 1);
  const material = new THREE.MeshStandardMaterial({ color: "red" });
  const vehicle = new THREE.Mesh(geometry, material);
  vehicle.position.set(x, 0.25, z);
  scene.add(vehicle);
  vehicles.push(vehicle);
}

// === Pozycje startowe (symulacja z JSON-a) ===
const vehicleData = [
  { x: 1.5, z: 3 },
  { x: -1.5, z: -3 },
  { x: -1.5, z: -5 },
];
vehicleData.forEach((v) => createVehicle(v.x, v.z));

// === Funkcja animacji ruchu ===
function animate() {
  requestAnimationFrame(animate);

  // Symulacja ruchu pojazdów
  // vehicles.forEach((v, index) => {
  //   v.position.x += 0.01 * (index + 1);
  // });
  addGrass(5, 5);
  addGrass(-5, 5);
  addGrass(5, -5);
  addGrass(-5, -5);

  addZones(-0.1, -5, 0.05, 5);
  addZones(0.1, -5, 0.05, 5);
  addZones(-0.1, 5, 0.05, 5);
  addZones(0.1, 5, 0.05, 5);

  addZones(5, -0.1, 5, 0.05);
  addZones(5, 0.1, 5, 0.05);
  addZones(-5, -0.1, 5, 0.05);
  addZones(-5, 0.1, 5, 0.05);

  renderer.render(scene, camera);
}
animate();
