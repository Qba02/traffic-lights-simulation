const textureLoader = new THREE.TextureLoader();
const asphaltTexture = textureLoader.load("textures/cardboard-texture.jpg");
asphaltTexture.wrapS = asphaltTexture.wrapT = THREE.RepeatWrapping;
asphaltTexture.repeat.set(3, 3);
const roadMaterial = new THREE.MeshStandardMaterial({
  map: asphaltTexture,
  color: "rgb(127, 127, 127)",
  roughness: 0.7,
  metalness: 0.7,
});
const roadGeometry = new THREE.BoxGeometry(15, 15, 0.2);
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
  const grassGeometry = new THREE.BoxGeometry(4, 4, 0.1);
  const grassTexture = textureLoader.load("textures/grass-texture.jpg");
  grassTexture.wrapS = asphaltTexture.wrapT = THREE.RepeatWrapping;
  grassTexture.repeat.set(1, 1);
  const grassMaterial = new THREE.MeshStandardMaterial({
    map: grassTexture,
    color: "rgb(142, 173, 110)",
    roughness: 0.7,
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
    color: "rgb(237, 237, 237)",
  });
  const grass = new THREE.Mesh(grassGeometry, grassMaterial);
  grass.rotation.x = -Math.PI / 2;
  grass.position.y += 0.1;
  grass.position.z -= z;
  grass.position.x -= x;
  scene.add(grass);
}

function addConcrete(x, z, sizeX, sizeY) {
  const concreteGeometry = new THREE.BoxGeometry(sizeX, sizeY, 0.1);
  const concreteMaterial = new THREE.MeshStandardMaterial({
    color: "rgb(88, 88, 88)",
  });
  const concrete = new THREE.Mesh(concreteGeometry, concreteMaterial);
  concrete.rotation.x = -Math.PI / 2;
  concrete.position.y += 0.15;
  concrete.position.z -= z;
  concrete.position.x -= x;
  scene.add(concrete);
}


addGrass(5.5, 5.5);
addGrass(-5.5, 5.5);
addGrass(5.5, -5.5);
addGrass(-5.5, -5.5);

addZones(-0.1, -5, 0.05, 5);
addZones(0.1, -5, 0.05, 5);
addZones(-0.1, 5, 0.05, 5);
addZones(0.1, 5, 0.05, 5);

addZones(5, -0.1, 5, 0.05);
addZones(5, 0.1, 5, 0.05);
addZones(-5, -0.1, 5, 0.05);
addZones(-5, 0.1, 5, 0.05);

addConcrete(-5, -3, 5, 1);
addConcrete(-5, 3, 5, 1);
addConcrete(-3, 5, 1, 5);
addConcrete(-3, -5, 1, 5);

addConcrete(3, -5, 1, 5);
addConcrete(3, 5, 1, 5);
addConcrete(5, -3, 5, 1);
addConcrete(5, 3, 5, 1);
