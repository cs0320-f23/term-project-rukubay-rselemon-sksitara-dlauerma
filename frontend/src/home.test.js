import React from "react";
import { render, fireEvent } from "@testing-library/react";
import Home from "./home";
import Dashboard from "./dashboard";
import Login from "./Login";
import { BrowserRouter as Router } from 'react-router-dom';

test("Home component renders correctly", () => {
  const { getByText } = render(<Router><Home loggedIn={false} email="" setLoggedIn={() => {}} /> </Router>);
  const titleElement = getByText(/Spot-A-Match!/i);
  expect(titleElement).toBeInTheDocument();
});

test("Log in button works correctly", () => {
  const setLoggedIn = jest.fn();
  const button = getByText(/Log in/i);
  fireEvent.click(button);
  const { getByText } = render(<Router><Home email=""  setLoggedIn={setLoggedIn} /> </Router>);
  const buttonElement = getByText(/Sign up/i);

  fireEvent.click(buttonElement);

  expect(buttonElement).toBeInTheDocument();
});


test("Login component renders correctly", () => {
    const { getByText } = render(<Router><Login /></Router>);
    const titleElement = getByText(/Sign up/i);
    expect(titleElement).toBeInTheDocument();
  });
  
  test("Validates email and password fields", async () => {
    const { getByText, getByPlaceholderText } = render(<Login />);
    const buttonElement = getByText(/Sign up/i);
  
    fireEvent.click(buttonElement);
  
    await waitFor(() => {
      expect(getByText(/Please enter your email/i)).toBeInTheDocument();
      expect(getByText(/Please enter a password/i)).toBeInTheDocument();
    });
  
    fireEvent.change(getByPlaceholderText(/Enter your email here/i), { target: { value: "invalid-email" } });
    fireEvent.change(getByPlaceholderText(/Enter your password here/i), { target: { value: "short" } });
  
    fireEvent.click(buttonElement);
  
    await waitFor(() => {
      expect(getByText(/Please enter a valid email/i)).toBeInTheDocument();
      expect(getByText(/The password must be 8 characters or longer/i)).toBeInTheDocument();
    });
  });

  test("Dashboard component renders correctly", () => {
    const { getByText } = render(<Dashboard />);
    const titleElement = getByText(/Listen Data/i);
    expect(titleElement).toBeInTheDocument();
  });
  