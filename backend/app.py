from flask import Flask, request, jsonify
from flask_cors import CORS
from db import SessionLocal, init_db
from models import MoodEntry

app = Flask(__name__)
CORS(app)

init_db()  

@app.route('/log-mood', methods=['POST'])
def log_mood():
    data = request.get_json()
    session = SessionLocal()
    mood = MoodEntry(mood=data['mood'], note=data.get('note', ''))
    session.add(mood)
    session.commit()
    session.close()
    return jsonify({'message': 'Mood logged'}), 200

@app.route('/get-moods', methods=['GET'])
def get_moods():
    session = SessionLocal()
    moods = session.query(MoodEntry).order_by(MoodEntry.timestamp.desc()).all()
    session.close()
    return jsonify([
        {
            'id': mood.id,
            'mood': mood.mood,
            'note': mood.note,
            'timestamp': mood.timestamp.isoformat()
        } for mood in moods
    ])

if __name__ == '__main__':
    app.run(debug=True)