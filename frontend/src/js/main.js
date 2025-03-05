import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";

const loader = new GLTFLoader();
let vehicleModel = null;

function loadModel(path, createVehicles) {
  loader.load(
    path,
    function (gltf) {
      vehicleModel = gltf.scene;
      console.log("Model loaded successfully");
      createVehicles();
    },
    function (xhr) {
      console.log((xhr.loaded / xhr.total) * 100 + "% loaded");
    },
    function (error) {
      console.log("Error during model loading ", error);
    }
  );
}

const vehicles = [];
const HALF_PI = Math.PI / 2;

function createVehicles1() {
  const vehicleData = [
    { x: 1.25, z: 3.5, direction: "forward", rotation: HALF_PI * 2 },
    { x: -1.25, z: -7, direction: "left", rotation: 0 },
    { x: -5, z: 1.25, direction: "none", rotation: HALF_PI },
  ];

  vehicleData.forEach((v) => createVehicle(v.x, v.z, v.direction, v.rotation));
}

function createVehicles2() {
  const vehicleData = [
    { x: -1.25, z: -3.5, direction: "backward", rotation: 0 },
    { x: 5, z: -1.25, direction: "none", rotation: -HALF_PI },
  ];

  vehicleData.forEach((v) => createVehicle(v.x, v.z, v.direction, v.rotation));
}

function createVehicle(x, z, direction, rotation) {
  if (!vehicleModel) return;

  const vehicle = vehicleModel.clone();
  vehicle.position.set(x, 0.1, z);
  vehicle.rotation.y = rotation;
  vehicle.userData = { direction, angle: 0 };

  scene.add(vehicle);
  vehicles.push(vehicle);
}

function animate() {
  requestAnimationFrame(animate);
  const speed = 0.015;
  const loopBoundary = 7.5;

  vehicles.forEach((v) => {
    if (v.userData.direction === "forward") {
      v.position.z -= speed;
      if (v.position.z < -loopBoundary) v.position.z = loopBoundary;
    } else if (v.userData.direction === "backward") {
      v.position.z += speed;
      if (v.position.z > loopBoundary) v.position.z = -loopBoundary;
    } else if (v.userData.direction === "left") {
      if (v.position.z > 1.25) {
        v.rotation.y = HALF_PI;
        v.position.x += speed;
      } else {
        v.position.z += speed;
      }

      if (v.position.x > loopBoundary) {
        v.position.x = -1.25;
        v.position.z = -5;
        v.rotation.y = 0;
      }
    }
  });
}

loadModel("car/scene.gltf", createVehicles1);
loadModel("car2/scene.gltf", createVehicles2);
animate();
