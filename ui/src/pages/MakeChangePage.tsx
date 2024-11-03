import { useFusionAuth } from "@fusionauth/react-sdk";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const dollarUS = Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  useGrouping: false,
});

type Change = { total: number; nickels: number; pennies: number } | null;

export default function MakeChangePage() {
  const [amount, setAmount] = useState<number>(0);
  const [change, setChange] = useState<Change | undefined>(undefined);

  const navigate = useNavigate();

  const { isLoggedIn, isFetchingUserInfo } = useFusionAuth();

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/");
    }
  }, [isLoggedIn, navigate]);

  const makeChange = async (e: React.MouseEvent<HTMLFormElement>) => {
    e.stopPropagation();
    e.preventDefault();

    const { data } = await axios.get<Change>('http://localhost:8080/make-change', {
      params: {
        total: amount
      },
      withCredentials: true,
      headers: {
        'Content-Type': 'application/json'
      }
    });
    setChange(data);
  };

  if (!isLoggedIn || isFetchingUserInfo) {
    return null;
  }

  return (
    <div className="app-container">
      <form className="change-container" onSubmit={makeChange}>
        <h3>Make Change</h3>
        <div className="change-label">Amount in USD</div>
        <input
          className="change-input"
          name="amount"
          value={amount}
          onChange={(e) => setAmount(parseFloat(e.target.value))}
          type="number"
          step=".01"
        />
        <input className="change-submit" type="submit" value="Make Change" />
      </form>
      {change && (
        <div className="change-message">
          We can make change for {dollarUS.format(change.total)} with{" "}
          {change.nickels} nickels and {change.pennies} pennies!
        </div>
      )}
    </div>
  );
}
