import React, { useState } from 'react';
import './ProfileSettings.css';
import InputField from '../../components/inputfield/InputField';

export default function ProfileSettings() {
  
    const [name] = useState('John');
    const [email, setEmail] = useState('john@doe.net');
    const [password, setPassword] = useState('1234');
    const [isEditing, setIsEditing] = useState(null); 
    
    const handleEdit = (field) => {
      setIsEditing(field);
    };

    const handleSave = () => {
      setIsEditing(null);
    };

    const handleKeyDown = (event, field) => {
      if (event.key === 'Enter'){
        if (field === 'email') {
          setEmail(event.target.value);
        } else if (field === 'password') {
          setPassword(event.target.value);
        }
        handleSave();
      }
    };

    return (
    <div className='profile-settings'>
      <h2>Profil Einstellungen</h2>
      <div className='profileField'>
        <label>Dein Name</label>
        <div><InputField
                label={name}
                disabled={true}
              ></InputField></div>
      </div>
      <div className='profileField'>
        <label>Email Adresse</label>
        {isEditing === 'email' ? (
          <InputField
              type='text'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              onKeyDown={(e) => handleKeyDown(e, 'email')}
            />
          ) : (<div>
            <InputField
            label={email}
            disabled={true}
          ></InputField>
            <button onClick={() => handleEdit('email')}>ändern</button></div>
        )}
      </div>
      <div className='profileField'>
        <label>Passwort</label>
        {isEditing === 'password' ? (
          <InputField
            type='password'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
              onKeyDown={(e) => handleKeyDown(e, 'password')}
            />
          ) : (<div>
              <InputField
                type={'password'}
                label={password}
                disabled={true}
              ></InputField>
            <button onClick={() => handleEdit('password')}>ändern</button></div>
        )}
      </div>
    </div>
  );
}

