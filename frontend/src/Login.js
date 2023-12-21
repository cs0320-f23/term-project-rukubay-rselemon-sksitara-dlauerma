import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

//const oAuthURI = "";

const Login = (props) => {
  //const [email, setEmail] = useState("");
  const { email, setEmail } = props;
  const [password, setPassword] = useState("");
  const [emailError, setEmailError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  async function getURI() {
    return await fetch(
      "http://localhost:3232/api/login?state=" + email + "|" + password
    )
      .then((response) => response.json())
      .then((json) => {
        if (json["result"] == "success") {
          return json["uri"];
        } else {
          return "";
        }
      });
  }

  const navigate = useNavigate();

  const onButtonClick = () => {
    // Set initial error values to empty
    setEmailError("");
    setPasswordError("");

    // Check if the user has entered both fields correctly
    if ("" === email) {
      setEmailError("Please enter your email");
      return;
    }

    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)) {
      setEmailError("Please enter a valid email");
      return;
    }

    if ("" === password) {
      setPasswordError("Please enter a password");
      return;
    }

    if (password.length < 7) {
      setPasswordError("The password must be 8 characters or longer");
      return;
    } else {
      getURI().then((oAuthURI) => {
        window.location.href = oAuthURI;
      });
    }
  };

  return (
    <div className={"mainContainer"}>
      <div className={"titleContainer"}>
        <div>Sign up</div>
      </div>
      <br />
      <div className={"inputContainer"}>
        <input
          value={email}
          placeholder="Enter your email here"
          onChange={(ev) => {
            setEmail(ev.target.value);
          }}
          className={"inputBox"}
        />
        <label className="errorLabel">{emailError}</label>
      </div>
      <br />
      <div className={"inputContainer"}>
        <input
          value={password}
          placeholder="Enter your password here"
          onChange={(ev) => setPassword(ev.target.value)}
          className={"inputBox"}
        />
        <label className="errorLabel">{passwordError}</label>
      </div>
      <br />
      <div className={"inputContainer"}>
        <input
          className={"inputButton"}
          type="button"
          onClick={onButtonClick}
          value={"Sign up"}
        />
      </div>
    </div>
  );
};

export default Login;
