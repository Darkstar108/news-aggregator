import HeaderBar from "./components/HeaderBar";
import AlertPanel from "./components/AlertPanel";
import NewsPanel from "./components/NewsPanel";
import "./App.css";

function App() {
  return (
    <div>
      <HeaderBar></HeaderBar>
      <div className="body">
        <AlertPanel></AlertPanel>
        <NewsPanel></NewsPanel>
      </div>
    </div>
  );
}

export default App;
