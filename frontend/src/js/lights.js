const HALF_PI = Math.PI / 2;

function createTrafficLights(x, y, z, rotation, redLightOn) {
  const trafficLightGroup = new THREE.Group();

  const poleGeometry = new THREE.CylinderGeometry(0.08, 0.08, 1, 32);
  const poleMaterial = new THREE.MeshStandardMaterial({ color: "rgb(0, 0, 0)", metalness: 0.8 });
  const pole = new THREE.Mesh(poleGeometry, poleMaterial);
  pole.position.set(x, y, z);
  trafficLightGroup.add(pole);

  const frameGeometry = new THREE.BoxGeometry(0.5, 1, 0.3);
  const frameMaterial = new THREE.MeshStandardMaterial({ color: "rgb(0, 0, 0)", metalness: 0.8  });
  const frame = new THREE.Mesh(frameGeometry, frameMaterial);
  frame.position.set(x, y + 1, z);
  trafficLightGroup.add(frame);

  redLight = createLight("rgb(202, 0, 0)", x, y - 0.7, z + 0.15);
  yellowLight = createLight("rgb(205, 181, 0)", x, y - 1.0, z + 0.15);
  greenLight = createLight("rgb(52, 183, 0)", x, y - 1.3, z + 0.15);
  trafficLightGroup.add(redLight);
  trafficLightGroup.add(yellowLight);
  trafficLightGroup.add(greenLight);

  let pointLight;
  if (redLightOn) {
    pointLight = new THREE.PointLight("rgb(255, 0, 0)", 3, 3);
    pointLight.position.set(x-1, y+1, z+1);
  } else {
    pointLight = new THREE.PointLight("rgb(69, 235, 4)", 3, 3);
    pointLight.position.set(x-1, y+1, z+1);
  }

  trafficLightGroup.add(pointLight);
  trafficLightGroup.rotation.y = rotation;

  scene.add(trafficLightGroup);

  return trafficLightGroup;
}

function createLight(lightColor, x, y, z) {
  const lightGeometry = new THREE.CylinderGeometry(0.12, 0.12, 0.05, 32);
  const lightMaterial = new THREE.MeshBasicMaterial({ color: lightColor });
  const light = new THREE.Mesh(lightGeometry, lightMaterial);
  light.position.set(x, y + 2, z);
  light.rotation.x = -HALF_PI;

  return light;
}

const trafficLight = createTrafficLights(3, 0.5, 3, 0, false);
const trafficLight2 = createTrafficLights(3, 0.5, 3, -HALF_PI * 2, false);
const trafficLight3 = createTrafficLights(3, 0.5, 3, HALF_PI, true);
const trafficLight4 = createTrafficLights(3, 0.5, 3, -HALF_PI, true);
