from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy.ext.declarative import declarative_base
from datetime import datetime

Base = declarative_base()

class MoodEntry(base):
    __tablename__ = 'mood_entries'
    id = Column(Integer, primary_key=True)
    mood = Column(String)
    note = Column(String)
    timestamp = Column(DateTime, default=datetime.utcnow)