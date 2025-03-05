async function requestSimulationResults() {
    try {
        const response = await fetch("src/assets/input.json");
        const jsonData = await response.json();
        
        const postResponse = await fetch("http://localhost:8080/simulation", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(jsonData)
        });

        const responseData = await postResponse.json();
        console.log("Response", responseData);
        downloadJsonFile(responseData);
    } catch (error) {
        console.error("Error:", error);
    }

}

async function downloadJsonFile(jsonData) {
    try {
        const blob = new Blob([JSON.stringify(jsonData, null, 2)], { type: "application/json" });
        const url = URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = "output.json";
        document.body.appendChild(a);
        a.click();

        URL.revokeObjectURL(url);
        document.body.removeChild(a);
        
    } catch (error) {
        console.error("Error: ", error);
    }
}


