import { useFusionAuth } from '@fusionauth/react-sdk';
import Snackbar, { SnackbarCloseReason } from '@mui/material/Snackbar';
import axios from "axios";
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

type PanicResponse = { message: string }

export default function PanicPage() {
  const { isLoggedIn } = useFusionAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/");
    }
  }, [isLoggedIn, navigate]);

  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState('asdf');

  const handleClose = (
    _: React.SyntheticEvent | Event,
    reason?: SnackbarCloseReason,
  ) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);
  };

  const panic = async () => {
    const { data } = await axios.post<PanicResponse>('http://localhost:8080/panic', {}, {
      withCredentials: true
    });
    const { message } = data;
    setMessage(message);
    setOpen(true);
  };

  return (
    <div className="app-container">
      <button
        className="button-lg"
        style={{ cursor: "pointer" }}
        onClick={(_) => panic()}
      >
        Panic
      </button>
      <Snackbar
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
        open={open}
        autoHideDuration={5000}
        onClose={handleClose}
        message={message}
      />
    </div>
  );
}
