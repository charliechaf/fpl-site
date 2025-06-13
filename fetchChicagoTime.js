export async function fetchChicagoTime() {
  try {
    const res = await fetch('https://worldtimeapi.org/api/timezone/America/Chicago');
    const json = await res.json();
    if (json.datetime) {
      return new Date(json.datetime).toLocaleString('en-US', { timeZone: 'America/Chicago' });
    }
    return '';
  } catch (e) {
    return 'Error fetching time';
  }
}
