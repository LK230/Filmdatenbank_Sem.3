import React, { useEffect, useState } from 'react';
import './ProfileSettings.css';
import InputField from '../../components/inputfield/InputField';
import { UserService } from '../../assets/service/user_service';
import Cookies from "js-cookie";

export default function ProfileSettings() {
  
    const [name, setName] = useState('');
    const [newEmail, setNewEmail] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [isEditing, setIsEditing] = useState(null); 
    const userService = new UserService();
    const myEmail = Cookies.get("email");
    const myPassword = Cookies.get("password");
    const [email, setEmail] = useState(myEmail);
    const [password, setPassword] = useState(myPassword);

    const handleEdit = (field) => {
      setIsEditing(field);
    };
    useEffect(() => {
      const fetchUserData = async () => {
        try {  
          const user = await userService.getUserMe(myEmail, myPassword)
          console.log('user', user) 
          setName(user.name)
        } catch (error) {
          console.log("Fehler beim Laden des Users", error)
         }
      } 
      fetchUserData()
   }, [])
    const handleSave = () => {
      setIsEditing(null);
    };

    const handleKeyDown = async(event, field) =>  {
      if (event.key === 'Enter'){
        if (field === 'email') {
          try {
            await userService.userPatchUpdateEmail(myEmail, myPassword, event.target.value)
            Cookies.set('email', event.target.value)
            setNewEmail(event.target.value);
          } catch (error) {
            console.log('Fehler beim Ändern der E-Mail Adresse', error)
          }

        } else if (field === 'password') {
          try {
            await userService.userPatchUpdatePassword(email, myPassword, event.target.value)
            Cookies.set('password', event.target.value)
            setNewPassword(event.target.value);
          } catch (error) {
            console.log('Fehler beim Ändern des Passworts', error)
          }
          
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
              value={newEmail || email}
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
            value={ newPassword || password }
            onChange={(e) => setPassword(e.target.value)}
              onKeyDown={(e) => handleKeyDown(e, 'password')}
            />
          ) : (<div>
              <InputField
                type='password'
                value={myPassword}
                disabled={true}
              ></InputField>
            <button onClick={() => handleEdit('password')}>ändern</button></div>
        )}
      </div>
    </div>
  );
}

