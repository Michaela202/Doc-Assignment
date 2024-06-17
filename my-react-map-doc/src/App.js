import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const departments = ['Technology', 'Human Resources', 'Marketing'];
  const [stories, setStories] = useState([]);
  const [newStory, setNewStory] = useState("");
  const [newStoryContent, setNewStoryContent] = useState("");
  const [newStoryDepartment, setNewStoryDepartment] = useState(departments[0]);
  const [editStoryId, setEditStoryId] = useState(null);
  const [editStoryTitle, setEditStoryTitle] = useState("");
  const [editStoryContent, setEditStoryContent] = useState("");
  const [editStoryDepartment, setEditStoryDepartment] = useState(departments[0]);
  const [selectedDepartment, setSelectedDepartment] = useState(departments[0]);

  useEffect(() => {
    fetchStories();
  }, []);

  const fetchStories = () => {
    fetch('http://localhost:8080/api/tabloids')
      .then(response => response.json())
      .then(data => setStories(data));
  };

  const addStory = () => {
    fetch('http://localhost:8080/api/tabloids', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ title: newStory, content: newStoryContent, department: newStoryDepartment }),
    })
    .then(response => response.json())
    .then(() => {
      setNewStory("");
      setNewStoryContent("");
      setNewStoryDepartment(departments[0]);
      fetchStories(); // Reload stories
    });
  };

  const deleteStory = (id) => {
    fetch(`http://localhost:8080/api/tabloids/${id}`, {
      method: 'DELETE',
    })
    .then(() => fetchStories());
  };

  const updateStory = () => {
    fetch(`http://localhost:8080/api/tabloids/${editStoryId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ title: editStoryTitle, content: editStoryContent, department: editStoryDepartment }),
    })
    .then(response => response.json())
    .then(() => {
      setEditStoryId(null);
      setEditStoryTitle("");
      setEditStoryContent("");
      setEditStoryDepartment(departments[0]);
      fetchStories(); // Reload stories
    });
  };

  const startEdit = (story) => {
    setEditStoryId(story.id);
    setEditStoryTitle(story.title);
    setEditStoryContent(story.content);
    setEditStoryDepartment(story.department);
  };

  return (
    <div className="App">
      <h1>VIA Tabloid 2</h1>
      <div className="tabs">
        {departments.map(department => (
          <button
            key={department}
            className={selectedDepartment === department ? 'active' : ''}
            onClick={() => setSelectedDepartment(department)}
          >
            {department}
          </button>
        ))}
      </div>
      <div className="new-story-section">
        <input 
          type="text" 
          value={newStory} 
          onChange={e => setNewStory(e.target.value)} 
          placeholder="New story title"
        />
        <textarea
          value={newStoryContent}
          onChange={e => setNewStoryContent(e.target.value)}
          placeholder="New story content"
        />
        <select
          value={newStoryDepartment}
          onChange={e => setNewStoryDepartment(e.target.value)}
        >
          {departments.map(department => (
            <option key={department} value={department}>{department}</option>
          ))}
        </select>
        <button onClick={addStory}>Add Story</button>
      </div>
      <ul>
        {stories.filter(story => story.department === selectedDepartment).map(story => (
          <li key={story.id}>
            <div>
              <h2>{story.title}</h2>
              <p>{story.content}</p>
            </div>
            <button onClick={() => deleteStory(story.id)}>Delete</button>
            <button onClick={() => startEdit(story)}>Edit</button>
          </li>
        ))}
      </ul>
      {editStoryId && (
        <div className="edit-section">
          <h2>Edit Story</h2>
          <input 
            type="text" 
            value={editStoryTitle} 
            onChange={e => setEditStoryTitle(e.target.value)} 
            placeholder="Edit story title"
          />
          <textarea
            value={editStoryContent}
            onChange={e => setEditStoryContent(e.target.value)}
            placeholder="Edit story content"
          />
          <select
            value={editStoryDepartment}
            onChange={e => setEditStoryDepartment(e.target.value)}
          >
            {departments.map(department => (
              <option key={department} value={department}>{department}</option>
            ))}
          </select>
          <button onClick={updateStory}>Update Story</button>
        </div>
      )}
    </div>
  );
}

export default App;
